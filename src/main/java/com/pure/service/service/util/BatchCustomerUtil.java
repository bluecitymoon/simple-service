package com.pure.service.service.util;

import com.google.common.base.Splitter;
import com.pure.service.domain.FreeClassRecord;
import com.pure.service.domain.NewOrderResourceLocation;
import com.pure.service.repository.NewOrderResourceLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BatchCustomerUtil {

    @Autowired
    private NewOrderResourceLocationRepository newOrderResourceLocationRepository;

    public List<FreeClassRecord> batchAnalysisCustomer(String content) {

        List<FreeClassRecord> freeClassRecords = new ArrayList<>();
        String[] customerLines = content.split("\\r?\\n");

        String lastLocation = "";
        for (int i = 0; i < customerLines.length; i++) {

            if (i < 2) continue;

            String line = customerLines[i];
            List<String> elements = Splitter.on(",").splitToList(line);

            if (!CollectionUtils.isEmpty(elements) && elements.size() > 7) {

                String location = elements.get(1);
                if (StringUtils.isEmpty(location)) {
                    location = lastLocation;
                } else {
                    lastLocation = location;
                }
                NewOrderResourceLocation resourceLocation = newOrderResourceLocationRepository.findByName(location);
                if (resourceLocation == null) {
                    NewOrderResourceLocation newOrderResourceLocation = new NewOrderResourceLocation();
                    newOrderResourceLocation.setName(location);
                    newOrderResourceLocation.setDescription("数据导入过程未发现该地点而自动新增的地点数据");

                    resourceLocation = newOrderResourceLocationRepository.saveAndFlush(newOrderResourceLocation);
                }
                String name = elements.get(2).trim();
                String phone = elements.get(4).trim();

                FreeClassRecord freeClassRecord = new FreeClassRecord();
                if (StringUtils.isEmpty(name) || StringUtils.isEmpty(phone)) continue;

                String birthday = elements.get(3);
                if (!StringUtils.isEmpty(birthday) && birthday.contains(".")) {
                    String[] birthdayElements = birthday.split("\\.");

                    String year = "1900", month = "01", day = "01";
                    switch (birthdayElements.length) {
                        case 1:
                            year = birthdayElements[0];
                            break;
                        case 2:
                            year = birthdayElements[0];
                            month = birthdayElements[1];
                            if (month.length() == 1) {
                                month = "0" + month;
                            }
                            break;
                        case 3:
                            year = birthdayElements[0];
                            month = birthdayElements[1];
                            day = birthdayElements[2];

                            if (month.length() == 1) {
                                month = "0" + month;
                            }
                            if (day.length() == 1) {
                                day = "0" + day;
                            }
                            break;
                        default:
                            break;
                    }

                    String wellBirthdayString = year + "-" + month + "-" + day + "T00:01:00.00Z";
                    try {
                        freeClassRecord.setBirthday(Instant.parse(wellBirthdayString));
                    } catch (DateTimeParseException ex) {
                        //
                    }

                }
                String school = elements.get(5);
                String freeCourses = elements.get(6);
                String comments = elements.get(7);

                freeClassRecord.setPersonName(name);
                freeClassRecord.setContactPhoneNumber(phone);
                freeClassRecord.setSchool(school);
                freeClassRecord.setNewOrderResourceLocation(resourceLocation);
                freeClassRecord.setComments(comments);

                freeClassRecords.add(freeClassRecord);
                System.out.println(freeClassRecord.toString());
            }
        }
        return freeClassRecords;
    }

//    public static void main(String[] args) throws IOException {
//        String content = FileUtils.readFileToString(new File("/Users/Jerry/Documents/Simple/batch_customers.csv"));
//
////        System.out.println(content);
//
//        batchAnalysisCustomer(content);
//
//    }
}
