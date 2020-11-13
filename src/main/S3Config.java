package main;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

import java.io.File;

public class S3Config {

    private static final String REGION = "us-west-2";

    private static final AWSCredentials credentials = new BasicAWSCredentials();

    private static AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withRegion(REGION)
            .withCredentials(new AWSStaticCredentialsProvider(credentials))
            .build();


    public static void upload(String bucketName, File file) {
        try {
            System.out.println("Uploading file \"" + file.getName()
                    + "\" to S3 bucket" + bucketName);
            TransferManager tx = TransferManagerBuilder
                    .standard()
                    .withS3Client(amazonS3)
                    .build();
            final Upload upload = tx.upload(bucketName, file.getName(), file);
            upload.addProgressListener(new ProgressListener() {
                public void progressChanged(ProgressEvent progressEvent) {
                    System.out.println("Transfer: " + upload.getDescription());
                    System.out.println("State: " + upload.getState());
                    System.out.println("Progress: "
                            + progressEvent.getBytesTransferred());
                }
            });
        } catch(AmazonServiceException e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }

    }
}
