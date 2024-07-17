class Student implements java.io.Serializable {
    private String id;
    private String name;
    private Module module;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setModule(int module1, int module2, int module3) {
        this.module = new Module(module1, module2, module3);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Module getModule() {
        return module;
    }
}
