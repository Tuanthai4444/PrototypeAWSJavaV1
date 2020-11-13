package main;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.transcribe.AmazonTranscribe;
import com.amazonaws.services.transcribe.AmazonTranscribeClientBuilder;
import com.amazonaws.services.transcribe.model.LanguageCode;
import com.amazonaws.services.transcribe.model.Media;
import com.amazonaws.services.transcribe.model.StartTranscriptionJobRequest;

public class startTranscribe {

    private static final String REGION = "us-west-2";



    //the actual transcriber
    private AmazonTranscribe amazonTranscribe = AmazonTranscribeClientBuilder
            .standard()
            .withRegion(REGION)
            .withCredentials(new ProfileCredentialsProvider())
            .build();

    //starting the transcription job
    public static void startTranscribeJob(String bucketName, String fileName) {
        StartTranscriptionJobRequest request = new StartTranscriptionJobRequest();
        Media media = new Media();
        media.setMediaFileUri(.getUrl(bucketName, fileName).toString());

        request.withMedia(media)
                .withLanguageCode(LanguageCode.EnUS)
                .withMediaSampleRateHertz(8000)
                .withMediaFormat("wav")
                .withOutputBucketName("placeholder")
                .setTranscriptionJobName("myJob");

    }
}
