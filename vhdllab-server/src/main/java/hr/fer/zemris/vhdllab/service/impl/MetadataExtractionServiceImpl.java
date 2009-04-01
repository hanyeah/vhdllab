package hr.fer.zemris.vhdllab.service.impl;

import hr.fer.zemris.vhdllab.service.MetadataExtractionService;
import hr.fer.zemris.vhdllab.service.ci.CircuitInterface;
import hr.fer.zemris.vhdllab.service.exception.CircuitInterfaceExtractionException;
import hr.fer.zemris.vhdllab.service.exception.VhdlGenerationException;
import hr.fer.zemris.vhdllab.service.result.Result;

public class MetadataExtractionServiceImpl extends ServiceSupport implements
        MetadataExtractionService {

    @Override
    public CircuitInterface extractCircuitInterface(Integer fileId)
            throws CircuitInterfaceExtractionException {
        return metadataExtractor.extractCircuitInterface(loadFile(fileId));
    }

    @Override
    public Result generateVhdl(Integer fileId) throws VhdlGenerationException {
        return metadataExtractor.generateVhdl(loadFile(fileId));
    }

}
