package hr.fer.zemris.vhdllab.platform.support;

import hr.fer.zemris.vhdllab.applets.main.VhdllabFrame;
import hr.fer.zemris.vhdllab.applets.main.component.projectexplorer.DefaultProjectExplorer;
import hr.fer.zemris.vhdllab.applets.view.compilation.CompilationErrorsView;
import hr.fer.zemris.vhdllab.applets.view.history.error.ErrorHistoryView;
import hr.fer.zemris.vhdllab.applets.view.history.status.StatusHistoryView;
import hr.fer.zemris.vhdllab.applets.view.simulation.SimulationErrorsView;
import hr.fer.zemris.vhdllab.platform.context.ApplicationContextHolder;
import hr.fer.zemris.vhdllab.platform.manager.shutdown.ShutdownManager;
import hr.fer.zemris.vhdllab.platform.manager.view.ViewManager;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public final class GuiInitializer implements Runnable {

    /**
     * Logger for this class
     */
    private static final Logger LOG = Logger.getLogger(GuiInitializer.class);

    @Autowired
    private ApplicationContext context;
    @Autowired
    ShutdownManager shutdownManager;
    @Autowired
    private ViewManager viewManager;

    public void initGUI() {
        try {
            SwingUtilities.invokeAndWait(this);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        LOG.debug("GUI initialized");
    }

    @Override
    public void run() {
        initLookAndFeel();
        initFrame();
    }

    private void initLookAndFeel() {
        try {
            if (!System.getProperty("os.name").equals("Linux")) {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private void initFrame() {
        JFrame frame = new VhdllabFrame(context);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        ApplicationContextHolder.getContext().setFrame(frame);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdownManager.shutdownWithConfirmation();
            }
        });
        ToolTipManager.sharedInstance().setDismissDelay(15000); // 15 seconds

        viewManager.open(DefaultProjectExplorer.class);

        viewManager.open(StatusHistoryView.class);
        viewManager.open(CompilationErrorsView.class);
        viewManager.open(SimulationErrorsView.class);
        if (ApplicationContextHolder.getContext().isDevelopment()) {
            viewManager.open(ErrorHistoryView.class);
        }
    }

}
