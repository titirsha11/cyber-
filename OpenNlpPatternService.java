package com.example.cybershield.ai;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class OpenNlpPatternService {
    private DoccatModel model;

    public void trainModel(List<String> suspectSamples, List<String> normalSamples) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos, StandardCharsets.UTF_8))) {
            for (String s : suspectSamples) {
                writer.write("suspect\t" + s + "\n");
            }
            for (String s : normalSamples) {
                writer.write("normal\t" + s + "\n");
            }
            writer.flush();
        }
        try (InputStream is = new ByteArrayInputStream(baos.toByteArray());
             ObjectStream<String> lineStream = new PlainTextByLineStream(() -> new InputStreamReader(is, StandardCharsets.UTF_8));
             ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream)) {
            this.model = DocumentCategorizerME.train("en", sampleStream);
        }
    }

    public boolean isSuspect(String text) {
        if (model == null || text == null) return false;
        DocumentCategorizerME categorizer = new DocumentCategorizerME(model);
        double[] outcomes = categorizer.categorize(text);
        String category = categorizer.getBestCategory(outcomes);
        return "suspect".equals(category);
    }
}
