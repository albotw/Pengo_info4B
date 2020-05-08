package com.generic.utils;

public class ScorePair {

        private String pseudo;
        private int score;
        private boolean local;

        public ScorePair(String pseudo, int score, boolean local)
        {
            this.pseudo = pseudo;
            this.score = score;
            this.local = local;
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

        public boolean isLocal()
        {
            return this.local;
        }

        public void setLocal(boolean local)
        {
            this.local = local;
        }
    }

