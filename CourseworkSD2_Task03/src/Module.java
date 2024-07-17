class Module implements java.io.Serializable {
    private int module1;
    private int module2;
    private int module3;

    public Module(int module1, int module2, int module3) {
        this.module1 = module1;
        this.module2 = module2;
        this.module3 = module3;
    }

    public int getModule1() {
        return module1;
    }

    public int getModule2() {
        return module2;
    }

    public int getModule3() {
        return module3;
    }

    public String getGrade() {
        int total = module1 + module2 + module3;
        double average = total / 3.0;
        if (average >= 70) {
            return "A";
        } else if (average >= 60) {
            return "B";
        } else if (average >= 50) {
            return "C";
        } else if (average >= 40) {
            return "D";
        } else {
            return "F";
        }
    }
}
