import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Student {
    protected static Integer nextId = 0;

    protected Integer id;
    protected String firstName;
    protected String lastName;
    protected Date birthday;
    protected List<Integer> grades;

    public Student(String firstName, String lastName, Date birthday) {
        this.id = nextId++;
        this.firstName = firstName;
        this.lastName = lastName;
        this.setBirthday(birthday);
        this.grades = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public void addGrade(Integer grade) {
        if (grade <= 0 || grade > 5) {
            throw new IllegalArgumentException("Známka může být pouze v rozmezí 1-5");
        }

        grades.add(grade);
    }

    public Float getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0f;
        }

        Integer sum = 0;
        for (Integer grade : grades) {
            sum += grade;
        }
        return (float) (sum / grades.size());
    }

    public abstract void performSkill();

    private void setBirthday(Date birthday) {
        if (birthday == null || birthday.after(new Date())) throw new IllegalArgumentException("Neplatné datum narození");

        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return this.getId() + "\t" + this.getFirstName() + " " + this.getLastName() + "\t" + this.getBirthday();
    }
}
