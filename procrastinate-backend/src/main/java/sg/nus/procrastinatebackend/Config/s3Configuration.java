package sg.nus.procrastinatebackend.Config;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class s3Configuration {

    private Logger logger = Logger.getLogger(s3Configuration.class.getName());
    
    @Value("${spaces.endpoint}")
    private String endpoint;
    
    @Value("${spaces.region}")
    private String region;

    @Value("${SECRET_KEY}")
    private String secretKey;

    @Value("${ACCESS_KEY}")
    private String accessKey;

    @Bean
    AmazonS3 createS3Client(){

        logger.log(Level.INFO, "Access Key > " + accessKey);
        logger.log(Level.INFO, "Secret Key > " + secretKey);

        // S3 credentials
        final BasicAWSCredentials basiccred = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider credProv = new AWSStaticCredentialsProvider(basiccred);

        final EndpointConfiguration endpointConfig = new EndpointConfiguration(endpoint, region);

        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(endpointConfig)
            .withCredentials(credProv)
            .build();
    }
    
}
