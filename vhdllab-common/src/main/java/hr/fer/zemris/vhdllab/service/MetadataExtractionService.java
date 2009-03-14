package hr.fer.zemris.vhdllab.service;

import hr.fer.zemris.vhdllab.api.hierarchy.Hierarchy;
import hr.fer.zemris.vhdllab.api.results.VHDLGenerationResult;
import hr.fer.zemris.vhdllab.entity.File;
import hr.fer.zemris.vhdllab.entity.Project;
import hr.fer.zemris.vhdllab.service.ci.CircuitInterface;
import hr.fer.zemris.vhdllab.service.exception.CircuitInterfaceExtractionException;
import hr.fer.zemris.vhdllab.service.exception.DependencyExtractionException;
import hr.fer.zemris.vhdllab.service.exception.VhdlGenerationException;

import java.util.Set;

public interface MetadataExtractionService {

    CircuitInterface extractCircuitInterface(File file)
            throws CircuitInterfaceExtractionException;

    Set<String> extractDependencies(File file)
            throws DependencyExtractionException;

    Hierarchy extractHierarchy(Project project);

    VHDLGenerationResult generateVhdl(File file) throws VhdlGenerationException;

}