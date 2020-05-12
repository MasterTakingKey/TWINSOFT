package application;

import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class AnimacionVoltear extends Animaciones {

	
	public AnimacionVoltear() {
		super();
	}
    
    public void crearAnimacion() {
    	stackPane.setDisable(true);
    	Node card = FlipAdelanteCard(imagen1);

        RotateTransition firstRotator = createFirstRotator(card);
        RotateTransition secondRotator = createSecondRotator(card);

        firstRotator.play();
        firstRotator.setOnFinished(e -> {
            imagen1.setImage(carta.imagenFrente);
               secondRotator.play();
        });
        secondRotator.setOnFinished(e -> {
            stackPane.setDisable(false);
     });
    }
}
