package model;

        public class NL {

            private String ten;
            private String donvi;
            private int soluongtrongkho;
            private double gianl;

            public NL(String ten, String donvi, int soluongtrongkho, double gianl) {

                this.ten = ten;
                this.donvi = donvi;
                this.soluongtrongkho = soluongtrongkho;
                this.gianl = gianl;
            }


            public String getTen() {
                return this.ten;
            }

            public void setTen(String ten) {
                this.ten = ten;
            }

            public String getDonvi() {
                return this.donvi;
            }

            public void setDonvi(String donvi) {
                this.donvi = donvi;
            }

            public int getSoluongtrongkho() {
                return this.soluongtrongkho;
            }

            public void setSoluongtrongkho(int soluongtrongkho) {
                this.soluongtrongkho = soluongtrongkho;
            }

            public double getGianl() {
                return this.gianl;
            }

            public void setGianl(double gianl) {
                this.gianl = gianl;
            }
        }