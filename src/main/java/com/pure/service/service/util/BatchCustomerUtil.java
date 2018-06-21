package com.pure.service.service.util;

import com.google.common.base.Splitter;
import com.pure.service.domain.FreeClassRecord;
import org.apache.commons.io.FileUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BatchCustomerUtil {

    public static List<FreeClassRecord> batchAnalysisCustomer(String content) {

        String[] customerLines = content.split("\\r?\\n");

        for (int i = 0; i < customerLines.length; i++) {

            if (i < 2) continue;

            String line = customerLines[i];
            List<String> elements = Splitter.on(",").splitToList(line);

            if (!CollectionUtils.isEmpty(elements) && elements.size() > 7) {

                String location = elements.get(1);
                String name = elements.get(2);
                String birthday = elements.get(3);
                String phone = elements.get(4);
                String school = elements.get(5);
                String freeCourses = elements.get(6);
                String comments = elements.get(7);

                FreeClassRecord freeClassRecord = new FreeClassRecord();
                freeClassRecord.setPersonName(name);
                freeClassRecord.setContactPhoneNumber(phone);

                System.out.println(freeClassRecord.toString());
            }


        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String content = FileUtils.readFileToString(new File("/Users/Jerry/Documents/Simple/batch_customers.csv"));

//        System.out.println(content);

        batchAnalysisCustomer(content);

    }
}
