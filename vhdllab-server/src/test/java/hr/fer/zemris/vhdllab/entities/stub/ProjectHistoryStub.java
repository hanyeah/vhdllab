package hr.fer.zemris.vhdllab.entities.stub;

import static hr.fer.zemris.vhdllab.entities.stub.TestsUtil.setField;
import hr.fer.zemris.vhdllab.entities.Caseless;
import hr.fer.zemris.vhdllab.entities.History;
import hr.fer.zemris.vhdllab.entities.ProjectHistory;

/**
 * @author Miro Bezjak
 * @version 1.0
 * @since vhdllab2
 */
public class ProjectHistoryStub extends ProjectHistory implements
        IProjectInfoHistoryStub {

    private static final long serialVersionUID = 1L;

    public ProjectHistoryStub() {
        super(new ProjectInfoStub(), new HistoryStub());
        setId(ID);
        setVersion(VERSION);
    }

    @Override
    public void setId(Integer id) {
        setField(this, "id", id);
    }

    @Override
    public void setVersion(Integer version) {
        setField(this, "version", version);
    }

    @Override
    public void setName(Caseless name) {
        setField(this, "name", name);
    }

    @Override
    public void setUserId(Caseless userId) {
        setField(this, "userId", userId);
    }

    @Override
    public void setHistory(History history) {
        setField(this, "history", history);
    }

}
