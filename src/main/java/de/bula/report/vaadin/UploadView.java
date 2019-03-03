package de.bula.report.vaadin;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.Route;
import de.bula.report.file.FileInput;
import de.bula.report.vaadin.layout.LayoutWithMenuBar;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

@Route(value = "UploadView", layout = LayoutWithMenuBar.class)

public class UploadView extends Composite<Div> {


    public UploadView(@Autowired FileInput fileInput) {

        final VerticalLayout root = new VerticalLayout();

        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setAcceptedFileTypes(".pdf");
//        upload.setAutoUpload(false);
        upload.setAutoUpload(true);


        upload.addSucceededListener(event -> {
            try {
                String path = "/Users/victoria/IdeaProjects/report/src/main/resources/static/" + event.getFileName();
                FileUtils.writeByteArrayToFile(
                        new File(path), IOUtils.toByteArray(buffer.getInputStream(event.getFileName())));

                fileInput.readFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            root.add(new Span("ok!"));
        });

        root.add(upload);
        getContent().add(root);
    }

}
