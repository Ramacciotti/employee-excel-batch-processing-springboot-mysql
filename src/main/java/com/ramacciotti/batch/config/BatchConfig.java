package com.ramacciotti.batch.config;

import com.ramacciotti.batch.listener.JobListener;
import com.ramacciotti.batch.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Configuração principal do Spring Batch para processamento de dados em lote.
 * Define o Job, Step, Reader e Writer usados para ler dados do CSV e salvar no banco.
 */
@Configuration
public class BatchConfig {

    private final PlatformTransactionManager transactionManager;

    public BatchConfig(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Configura o Job que representa o processo completo de lote.
     * Um job pode conter múltiplos steps (passos).
     *
     * @param jobRepository Gerencia a execução dos Jobs
     * @param step          Step que será executado pelo job
     * @param jobListener   Listener para logar início e fim do Job
     * @return Job configurado
     */
    @Bean
    public Job job(JobRepository jobRepository, Step step, JobListener jobListener) {
        return new JobBuilder("employeeJob", jobRepository)
                .start(step)
                .listener(jobListener)
                .incrementer(new RunIdIncrementer()) // Garante ID único a cada execução
                .build();
    }

    /**
     * Configura o Step que representa uma etapa do Job.
     * Define chunk (tamanho do lote), reader e writer.
     *
     * @param jobRepository Gerencia execução dos steps
     * @param reader        Leitor de dados (CSV)
     * @param writer        Escritor de dados (Banco)
     * @return Step configurado
     */
    @Bean
    public Step step(JobRepository jobRepository, ItemReader<Employee> reader, ItemWriter<Employee> writer) {
        return new StepBuilder("saveEmployeesToDatabase", jobRepository)
                .<Employee, Employee>chunk(10, transactionManager) // Processa 10 itens por vez (chunk)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }

    /**
     * Define o ItemReader que lê dados de um arquivo CSV.
     * Usamos @StepScope para que o reader seja gerenciado dentro do escopo do step,
     * o que permite o controle correto do ciclo de vida, incluindo abertura e fechamento.
     *
     * @return ItemReader configurado para ler employees.csv
     */
    @Bean
    @StepScope
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeItemReader")
                .resource(new ClassPathResource("employees.csv"))
                .delimited()
                .names("name", "title", "department", "age")
                .targetType(Employee.class)
                .build();
    }

    /**
     * Define o ItemWriter que grava dados na tabela employee do banco de dados.
     * Utiliza JdbcBatchItemWriter para inserções batch com parâmetros nomeados.
     *
     * @param dataSource DataSource para conexão com banco
     * @return ItemWriter configurado para inserir no banco
     */
    @Bean
    public ItemWriter<Employee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Employee>()
                .dataSource(dataSource)
                .sql("INSERT INTO employee (name, title, department, age) VALUES (:name, :title, :department, :age)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return employee -> {
            employee.setId(null);  // garante que id está null para o banco gerar
            return employee;
        };
    }

}