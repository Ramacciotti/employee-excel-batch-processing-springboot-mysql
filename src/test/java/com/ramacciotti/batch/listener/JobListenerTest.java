package com.ramacciotti.batch.listener;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JobListenerTest {

    private JobListener jobListener;

    @BeforeEach
    void setUp() {
        jobListener = new JobListener();
    }

    @Test
    @DisplayName("Should execute beforeJob without throwing exception")
    void testBeforeJob() {
        JobExecution mockJobExecution = mock(JobExecution.class);
        jobListener.beforeJob(mockJobExecution);
    }

    @Test
    @DisplayName("Should log success when afterJob receives COMPLETED status")
    void testAfterJobCompleted() {
        JobExecution mockJobExecution = mock(JobExecution.class);
        when(mockJobExecution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        jobListener.afterJob(mockJobExecution);
    }

    @Test
    @DisplayName("Should log error when afterJob receives FAILED status")
    void testAfterJobFailed() {
        JobExecution mockJobExecution = mock(JobExecution.class);
        when(mockJobExecution.getStatus()).thenReturn(BatchStatus.FAILED);
        jobListener.afterJob(mockJobExecution);
    }

    @Test
    @DisplayName("Should execute afterJob with no specific log for UNKNOWN status")
    void testAfterJobOtherStatus() {
        JobExecution mockJobExecution = mock(JobExecution.class);
        when(mockJobExecution.getStatus()).thenReturn(BatchStatus.UNKNOWN);
        jobListener.afterJob(mockJobExecution);
    }
}