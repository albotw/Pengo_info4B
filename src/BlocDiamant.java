public class BlocDiamant extends Entite {


    public BlocDiamant(int x, int y) {
        super(x, y);
    }

    public void Glissade(){

    }

    @Override
    void moove() {

    }

    @Override
    void isDead() {

    }

    public void SeTouche(){

    }

    public int CptD(){
        int cptD = 0;               // ici on a un compteur pour les blocs de diamant
        if(BlocDiamant."SeTouche"){ // A chaque fois que les bloc de diamant se touche on implémente le compteur de 1;
            cptD++;
        }
        return cptD;                // on return le nombre de compteur, si il est égale à 3 Penguoin gagne.
    }
}
