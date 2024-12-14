package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.RecordDAO;
import ru.itis.tjmoney.dto.RecordDTO;
import ru.itis.tjmoney.models.Record;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecordService {
    private final RecordDAO recordDAO;

    public RecordService(RecordDAO recordDAO) {
        this.recordDAO = recordDAO;
    }

    public List<Record> getUserRecords(int userId) {
        return recordDAO.findUserRecords(userId);
    }

    public List<Record> getGroupRecords(int groupId) {
        return recordDAO.findGroupRecords(groupId);
    }

    public List<Record> getUserAndGroupRecords(int userId, int groupId) {
        return groupId == 0 ? recordDAO.findUserRecords(userId) : recordDAO.findGroupRecords(groupId);
    }

    public Record getRecord(int recordId) {
        return recordDAO.findRecordById(recordId);
    }

    public RecordDTO getRecordDTO(int recordId) {
        Record record = getRecord(recordId);

        return new RecordDTO(
                record.getTitle(),
                record.getContent(),
                record.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                record.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    public void save(int userId, int groupId, String title, String content) {
        recordDAO.save(new Record(
                0,
                userId,
                groupId,
                title,
                content,
                LocalDateTime.now(),
                LocalDateTime.now()
        ));
    }

    public void delete(int id) {
        recordDAO.deleteById(id);
    }

    public void update(String title, String content, int id) {
        recordDAO.update(new Record(
                id,
                0,
                0,
                title,
                content,
                null,
                LocalDateTime.now()
        ));

        // здесь должна быть какая-то проверка
    }
}
