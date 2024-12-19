package ru.itis.tjmoney.services.interfaces;

import ru.itis.tjmoney.dto.RecordDTO;
import ru.itis.tjmoney.models.Record;

import java.util.List;

public interface IRecordService {
    List<Record> getUserAndGroupRecords(int userId, int groupId);
    Record getRecord(int recordId);
    RecordDTO getRecordDTO(int recordId);
    void save(int userId, int groupId, String title, String content);
    void delete(int id);
    void update(String title, String content, int id);
}
