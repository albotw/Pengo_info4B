package com.generic.gameplay;

public class ScorePair {

        private String pseudo;
        private int score;

        public ScorePair(String pseudo, int score)
        {
            this.pseudo = pseudo;
            this.score = score;
        }

        public String getPseudo() {
            return pseudo;
        }

        public void setPseudo(String pseudo) {
            this.pseudo = pseudo;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

