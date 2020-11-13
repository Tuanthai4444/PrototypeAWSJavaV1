package main;

import com.amazonaws.services.transcribe.AmazonTranscribe;
import com.amazonaws.services.transcribe.AmazonTranscribeClientBuilder;
import com.amazonaws.services.transcribe.model.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AmazonS3Transcription {

    private static final String REGION = "us-west-2";

    private AmazonTranscribe amazonTranscribe = AmazonTranscribeClientBuilder
            .standard()
            .withRegion(REGION)
            .build();
    /*
    private AmazonS3 amazonS3 = AmazonS3ClientBuilder
            .standard()
            .withRegion(REGION)
            .withClientConfiguration(new ClientConfiguration())
            .withCredentials(new DefaultAWSCredentialsProviderChain())
            .build();


    public void makeBucket(String bucketName, String fileName, File fullFileName) {
        amazonS3.putObject(bucketName, fileName, fullFileName);
    }
    */
    public void startTranscribeJob(String bucketName, String fileName) {
        StartTranscriptionJobRequest request = new StartTranscriptionJobRequest();
        Media media = new Media();
        media.setMediaFileUri(amazonS3.getUrl(bucketName, fileName).toString());

        request.withMedia(media)
                .withLanguageCode(LanguageCode.EnUS)
                .withMediaSampleRateHertz(8000)
                .withMediaFormat("wav")
                .withOutputBucketName("placeholder")
                .setTranscriptionJobName("myJob");
        try {
            amazonTranscribe.startTranscriptionJob(request);
        } catch(Amazon)
    }

    public void getJobResults(String fileName) throws IOException {
        GetTranscriptionJobRequest result = new GetTranscriptionJobRequest();
        result.setTranscriptionJobName("jobResults");
        TranscriptionJob transcriptionJob = amazonTranscribe.getTranscriptionJob(result)
                .getTranscriptionJob();
        if(transcriptionJob.getTranscriptionJobStatus()
                .equals(TranscriptionJobStatus.COMPLETED.name())) {
            String transcript = uriTranscribe
                    (transcriptionJob.getTranscript().getTranscriptFileUri());
        }
    }

    private String uriTranscribe(String uri) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(uri);
        StringEntity entity = new StringEntity();
        post.addHeader("content-type", "application/json");
        post.setEntity(entity);

        HttpResponse response = httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity(), "UTF-8");
        return result;
    }

}
