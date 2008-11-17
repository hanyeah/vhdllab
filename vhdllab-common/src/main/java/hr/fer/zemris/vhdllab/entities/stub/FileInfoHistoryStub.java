package hr.fer.zemris.vhdllab.entities.stub;

import static hr.fer.zemris.vhdllab.entities.stub.TestsUtil.setField;
import hr.fer.zemris.vhdllab.entities.Caseless;
import hr.fer.zemris.vhdllab.entities.FileInfoHistory;
import hr.fer.zemris.vhdllab.entities.FileType;
import hr.fer.zemris.vhdllab.entities.History;

/**
 * @author Miro Bezjak
 * @version 1.0
 * @since vhdllab2
 */
public class FileInfoHistoryStub extends FileInfoHistory implements
        IFileInfoHistoryStub {

    private static final long serialVersionUID = 1L;

    public FileInfoHistoryStub() {
        super(new FileInfoStub(), new HistoryStub());
        setId(ID);
        setVersion(VERSION);
    }

    public FileInfoHistoryStub(FileInfoHistory history) {
        super(history);
    }

    @Override
    public void setId(Integer id) {
        setField(this, "id", id);
    }

    @Override
    public void setName(Caseless name) {
        setField(this, "name", name);
    }

    @Override
    public void setVersion(Integer version) {
        setField(this, "version", version);
    }

    @Override
    public void setType(FileType type) {
        setField(this, "type", type);
    }

    @Override
    public void setProjectId(Integer projectId) {
        setField(this, "projectId", projectId);
    }

    @Override
    public void setHistory(History history) {
        setField(this, "history", history);
    }

}
