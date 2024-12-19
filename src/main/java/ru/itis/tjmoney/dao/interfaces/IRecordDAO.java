package ru.itis.tjmoney.dao.interfaces;

import ru.itis.tjmoney.models.Record;

import java.util.List;

public interface IRecordDAO {
    List<Record> findUserRecords(int userId);
    List<Record> findGroupRecords(int groupId);
    Record findRecordById(int recordId);
    Record save(Record record);
    void update(Record updatedRecord);
    void deleteById(int id);
}
