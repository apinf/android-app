package uk.co.ribot.APInf_application.ui.main;

import java.util.List;

import uk.co.ribot.APInf_application.data.model.Ribot;
import uk.co.ribot.APInf_application.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showRibots(List<Ribot> ribots);

    void showRibotsEmpty();

    void showError();

}
