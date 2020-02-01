package schoolmanagementservices;

import integration.CourseDAOImpl;
import integration.Factory;
import integration.ProgramDAOImpl;
import integration.StudentDAOImpl;
import integration.TeacherDAOImpl;
import view.CourseMenu;
import view.MainMenu;
import view.ProgramMenu;
import view.StudentMenu;
import view.TeacherMenu;
import view.UI;

public class Main {

    public static void main(String[] args) {
//        if drop and create
//        integration.PopulateDatabase.populateDatabase();
        UI ui;
        if (args.length==0) {
            ui = new view.UISwingImpl("School Management System");
        } else {
            ui = new view.UIScannerImpl();
        }

        TeacherManagement teacher = new TeacherManagement(new TeacherDAOImpl(Factory.emf), ui);
        CourseManagement course = new CourseManagement(new CourseDAOImpl(Factory.emf), ui);
        StudentManagement student = new StudentManagement(new StudentDAOImpl(Factory.emf), ui);
        ProgramManagement program = new ProgramManagement(new ProgramDAOImpl(Factory.emf), ui);

        teacher.setCourse(course);
        course.setProgram(program);
        course.setTeacher(teacher);
        student.setProgram(program);
        program.setCourse(course);
        program.setStudent(student);

        TeacherMenu teacherMenu = new TeacherMenu(ui, teacher);
        CourseMenu courseMenu = new CourseMenu(ui, course);
        StudentMenu studentMenu = new StudentMenu(ui, student);
        ProgramMenu programMenu = new ProgramMenu(ui, program);

        MainMenu mainMenu = new MainMenu(ui, teacherMenu, courseMenu, studentMenu, programMenu);
        mainMenu.mainMenu();
    }

}
