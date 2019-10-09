package com.guanzhou.Services;

import com.guanzhou.Model.UserInfo;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

@Service
public class ParseService {

  /**
   * get two list of UserInfo as duplicated and non-duplicated UserInfo.
   *
   * @param CSV_FILE_NAME path to csv file.
   * @return two list of UserInfo
   */
  public List<List<UserInfo>> getPossibleDupUser(String CSV_FILE_NAME, int META_LEN, int THRESHOLD) {
    ParseService p = new ParseService();
    List<UserInfo> info = null;
    try {
      info = p.readCSVByLine(CSV_FILE_NAME);
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (null == info || info.size() == 0) {
      return null;
    }

    List<UserInfo> dupUser = new LinkedList<>();
    LevenshteinDistance ld = new LevenshteinDistance();
    //get duplicated user.
    for (int i = 0; i < info.size() - 1; i++) {
      for (int j = i + 1; j < info.size() - 1; j++) {
        if (ld.apply(getMetaPhone(info.get(i).getAll(), META_LEN), getMetaPhone(info.get(j).getAll(), META_LEN))
            <= THRESHOLD) {
          dupUser.add(info.get(i));
          dupUser.add(info.get(j));
        }
      }
    }
    //create set to look up.
    Set<UserInfo> dupUserSet = new HashSet<>(dupUser);
    List<UserInfo> noDupUser = new LinkedList<>();
    for (UserInfo u : info) {
      if (!dupUserSet.contains(u)) {
        noDupUser.add(u);
      }
    }
    List<List<UserInfo>> res = new LinkedList<>();
    res.add(dupUser);
    res.add(noDupUser);
    return res;
  }


  /**
   * method to read from CSV file and parse into a List of UserInfo
   *
   * @param CSV_FILE_NAME name csv file
   * @return list of UserInfo
   * @throws IOException
   */
  private List<UserInfo> readCSVByLine(String CSV_FILE_NAME) throws IOException {
    //read in the csv file.
    String CSV_FILE_PATH = System.getProperty("user.dir") + "/data/" + CSV_FILE_NAME;
    Reader in = new FileReader(CSV_FILE_PATH);
    Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
    List<UserInfo> res = new LinkedList<>();

    for (CSVRecord csvRecord : records) {
      res.add(createUserInfoFromCSV(csvRecord));
    }
    return res;
  }

  /**
   * method to read in a csvrecored and parse into a UserInfo class.
   * @param csvRecord record contains user information.
   * @return UserInfo
   */
  private UserInfo createUserInfoFromCSV(CSVRecord csvRecord) {
    UserInfo u = new UserInfo();
    try{
      u.setId(csvRecord.get(0));
      u.setFirst_name(csvRecord.get(1));
      u.setLast_name(csvRecord.get(2));
      u.setCompany(csvRecord.get(3));
      u.setEmail(csvRecord.get(4));
      u.setAddress1(csvRecord.get(5));
      u.setAddress2(csvRecord.get(6));
      u.setZip(csvRecord.get(7));
      u.setCity(csvRecord.get(8));
      u.setState_long(csvRecord.get(9));
      u.setState(csvRecord.get(10));
      u.setPhone(csvRecord.get(11));
    }catch (Exception e){
      //invalid record.
      return null;
    }
    return u;
  }


  /**
   * get MetaPhone of a string, set max code length as META_len if not 0;
   *
   * @param s        string to be transformed
   * @param META_len 0 to use default, otherwise set as max code len for METAPHONE class.
   * @return transformed string.
   */
  private String getMetaPhone(String s, int META_len) {
    Metaphone mp = new Metaphone();
    if (META_len != 0) {
      mp.setMaxCodeLen(META_len);
    }
    return mp.metaphone(s);
  }


}