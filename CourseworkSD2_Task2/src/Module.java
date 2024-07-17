public class Module {
    private int module1;
    private int module2;
    private int module3;
    private String grade;

    public Module(int module1, int module2, int module3) {
        this.module1 = module1;
        this.module2 = module2;
        this.module3 = module3;
        calculateGrade();
    }

    private void calculateGrade() {
        int average = (module1 + module2 + module3) / 3;
        if (average >= 80) {
            grade = "Distinction";
        } else if (average >= 70) {
            grade = "Merit";
        } else if (average >= 40) {
            grade = "Pass";
        } else {
            grade = "Fail";
        }
    }

    public int getModule1() { return module1; }
    public int getModule2() { return module2; }
    public int getModule3() { return module3; }
    public String getGrade() { return grade; }
}

