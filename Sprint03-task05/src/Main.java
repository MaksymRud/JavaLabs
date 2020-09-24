import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static class Student {
        private int id;
        private String name;
        // Constructor, metthods, Code
        Student(int id, String name)
        {
            this.id = id;
            this.name = name;
        }
        @Override
        public boolean equals(Object o) {
            if (o == this){
                return true;
            }

            if (!(o instanceof Student)){
                return false;
            }
            Student s = (Student) o;
            if(s.name == null)
                return false;
            return Integer.compare(this.id, s.id) == 0 &&
                    this.name.compareTo(s.name) == 0;
        }

        public int hashcode(){
            int hash = 1;
            hash = hash * 31 + this.id;
            hash = hash*31 + (this.name == null ? 0 : this.name.hashCode());
            return hash;
        }
    }

    public Set<Student> commonStudents(List<Student> list1, List<Student> list2) {
        Set<Student> commonStud = new HashSet<Student>();
        for(Student s : list1) {
            for(Student s1 : list2){
                if(s1.equals(s)) {
                    commonStud.add(s);
                }
            }
        }
        return commonStud;
    }

    public static void main(String[] args) {
	// write your code here
    }
}
