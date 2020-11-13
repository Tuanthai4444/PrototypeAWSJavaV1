package main;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;

import java.util.List;

public class S3Bucket {

    private AmazonS3 S3Client;

    public S3Bucket(AmazonS3 S3Client) {
        this.S3Client = S3Client;
    }

    public boolean bucketExists(String bucketName) {
        return S3Client.doesBucketExistV2(bucketName);
    }

    public Bucket createBucket(String bucketName) {
        if(bucketExists(bucketName)) {
            throw new IllegalArgumentException("Bucket name already taken, try again.");
        } else {
            return S3Client.createBucket(bucketName);
        }
    }

    public void deleteBucket(String bucketName) {
        S3Client.deleteBucket(bucketName);
    }

    public void availableBuckets() {
        List<Bucket> allBuckets = S3Client.listBuckets();
        for (Bucket bucket : allBuckets) {
            System.out.println(bucket.getName());
        }
    }

}
