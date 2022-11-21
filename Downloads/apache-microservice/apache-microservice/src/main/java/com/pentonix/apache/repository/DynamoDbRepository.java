package com.pentonix.apache.repository;

import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.pentonix.apache.apiservice.WorkflowMaster;

@Repository
public class DynamoDbRepository {
//private static final Logger LOGGER = LoggerFactory.getLogger(DynamoDbRespository.class);
	
//	BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials("AKIAVUIOQSFU6R3ZA7L3", "NxqZEuLF4Zuj/QQ8/9FyO/++O7QqaIq6QfXXWrzC");
//    DynamoDB dynamoDB = (DynamoDB) AmazonDynamoDBClient.builder().withRegion("us-east-2").withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials)).build(); 
////    AmazonDynamoDBClient

	@Autowired
	private DynamoDBMapper mapper;
	
	public void insertIntoWorkflowMaster(WorkflowMaster workflowMaster) {
		mapper.save(workflowMaster);
	}
	public WorkflowMaster getWorkflow(String workflowId){
		return mapper.load(WorkflowMaster.class,workflowId);
	}
	public void deleteWorkflow(WorkflowMaster workflowMaster) {
		mapper.delete(workflowMaster);
	}

}
