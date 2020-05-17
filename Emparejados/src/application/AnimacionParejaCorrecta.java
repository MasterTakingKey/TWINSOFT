package application;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;

public class AnimacionParejaCorrecta extends Animaciones {

	
	public AnimacionParejaCorrecta() {
		super();
	}
	
	public void crearAnimacion() {
        stackPane.setDisable(true);
        Node card1 = FlipAdelanteCard(imagen1);
        Node card2 = FlipAdelanteCard(imagen2);

        TranslateTransition firstTranslation1 = createCorrectTranslation(card1);
        TranslateTransition firstTranslation2 = createCorrectTranslation(card2);

        firstTranslation1.play();
        firstTranslation2.play();

        firstTranslation2.setOnFinished(e -> {
            stackPane.setDisable(false);
        });

      }
}
