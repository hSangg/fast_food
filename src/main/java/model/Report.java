package model;

        public class Report{
    private int thang;

            private double doanhthu;
    private double kinhphi;
    public Report(int x,double y,double z){
                thang=x;
                doanhthu=y;
                kinhphi=z;

                    }

            public double getKinhphi() {
                return kinhphi;
            }

            public void setKinhphi(double kinhphi) {
                this.kinhphi = kinhphi;
            }

            public int getThang() {
                return thang;
            }

            public double getDoanhthu() {
                return doanhthu;
            }

            public void setDoanhthu(double doanhthu) {
                this.doanhthu = doanhthu;
            }

            public void setThang(int thang) {
                this.thang = thang;
            }
}