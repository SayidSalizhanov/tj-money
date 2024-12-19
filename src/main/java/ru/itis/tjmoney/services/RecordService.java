package ru.itis.tjmoney.services;

import ru.itis.tjmoney.dao.interfaces.IRecordDAO;
import ru.itis.tjmoney.dto.RecordDTO;
import ru.itis.tjmoney.models.Record;
import ru.itis.tjmoney.services.interfaces.IRecordService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecordService implements IRecordService {
    private final IRecordDAO recordDAO;

    public RecordService(IRecordDAO recordDAO) {
        this.recordDAO = recordDAO;
    }

    @Override
    public List<Record> getUserAndGroupRecords(int userId, int groupId) {
        return groupId == 0 ? recordDAO.findUserRecords(userId) : recordDAO.findGroupRecords(groupId);
    }

    @Override
    public Record getRecord(int recordId) {
        return recordDAO.findRecordById(recordId);
    }

    @Override
    public RecordDTO getRecordDTO(int recordId) {
        Record record = getRecord(recordId);

        return new RecordDTO(
                record.getTitle(),
                record.getContent(),
                record.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                record.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }

    @Override
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

    @Override
    public void delete(int id) {
        recordDAO.deleteById(id);
    }

    @Override
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
    }
}
