package schoolmanagementservices;

import entities.Course;
import entities.Teacher;
import integration.TeacherDAO;
import view.UI;

public class TeacherManagement {

    TeacherDAO daoTeacher;
    UI ui;
    CourseManagement course;

    public TeacherManagement(TeacherDAO teacherDAOImpl, UI ui) {
        daoTeacher = teacherDAOImpl;
        this.ui = ui;
    }

    public void setCourse(CourseManagement course) {
        this.course = course;
    }

    public Teacher findByNameOrID() {

        String input = ui.inputString();
        if (input.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        }
        if (daoTeacher.findByNameOrID(input).count() > 1) {
            daoTeacher.findByNameOrID(input).forEach(ui::printResult);
            ui.outputString("\nMore than one result.\nEnter ID of Teacher instead or search for another Teacher:");
            return findByNameOrID();
        }
        
        Teacher t = daoTeacher.findByNameOrID(input).findFirst().orElse(null);

        if (t != null) {
            showDetailedInfo(t);
        } else {
            ui.outputString("No result found");
        }
        return t;
    }

    public void showDetailedInfo(Teacher t) {
        ui.printResult(t);
        if (!t.getCourseList().isEmpty()) {
            ui.outputString("\nCurrent Courses:");
        }
        t.getCourseList().forEach(c -> ui.printResult(c
                + (c.getProgram() != null ? ". Program: " + c.getProgram().getName() : "")));
    }

    public void showAll() {
        daoTeacher.findAll().sorted((e1, e2) -> e1.getId() - e2.getId()).forEach(ui::printResult);
    }

    public Teacher add() {
        ui.outputString("Enter Teacher name:");
        String name = ui.inputString();
        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        } else if (daoTeacher.findByName(name) != null) {
            ui.outputString("Teacher with that name already exists, choose another name");
            return add();
        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Teacher name cannot be only numbers, try again");
            return add();
        }

        Teacher t = new Teacher(name);
        daoTeacher.create(t);
        ui.outputString("Teacher created");
        return t;
    }

    public void update(String choice) {
        ui.outputString("Enter Name or ID of Teacher to Update:");
        Teacher t = findByNameOrID();
        if (t == null) {
            if (choice.equals("addCourses")) {
                ui.pressEnterToContinue();
            }
            return;
        }
        switch (choice) {

            case "updateName":
                updateName(t);
                break;
            case "addCourses":
                addCourses(t);
                break;
            case "removeCourse":
                removeCourse(t);
                break;
        }

        daoTeacher.update(t);

    }

    public void updateName(Teacher t) {
        ui.outputString("Enter new Teacher name:");
        String name = ui.inputString();

        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");

        } else if (daoTeacher.findByName(name) != null) {
            ui.outputString("Teacher with that name already exists, choose another name");

        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Teacher name cannot be only numbers, try again");

        } else {
            t.setName(name);
            ui.outputString("Teacher Name updated");

        }
    }

    public void delete() {
        ui.outputString("Enter Name or ID of Teacher to Delete:");
        Teacher t = findByNameOrID();
        if (t != null) {
            daoTeacher.delete(t);
            ui.outputString(t.getName() + " removed successfully from Teachers");
        }
    }

    private void addCourses(Teacher t) {
        String input = "Y";

        while (input.equalsIgnoreCase("Y")) {
            ui.outputString("\nList of available Courses:");
            course.showAll();
            ui.outputString("\nEnter Name or ID of Courses to add to Teacher:");
            Course c = course.findByNameOrID();

            if (c == null) {
                ui.outputString("Create new Course? (Y/N)");
                input = ui.inputString();
                ui.clear();
                if (input.equalsIgnoreCase("Y")) {
                    c = course.add();
                } else {
                    return;
                }
            }
            if (c != null) {

                if (t.getCourseList().contains(c)) {
                    ui.outputString("\nCourse already added to Teacher " + t.getName());
                } else {
                    t.getCourseList().add(c);
                    c.getTeacherList().add(t);

                    course.daoCourse.update(c);
                    ui.outputString("\nCourse added to Teacher " + t.getName());
                }
            }
            ui.outputString("Add another course? (Y/N)");

            input = ui.inputString();
            ui.clear();

        }

    }

    private void removeCourse(Teacher t) {
        ui.outputString("Enter Name or ID of Course to remove from Teacher:");
        Course c = course.findByNameOrID();
        if (c != null) {
            t.getCourseList().remove(c);
            c.getTeacherList().remove(t);
            course.daoCourse.update(c);
            ui.outputString("Teacher removed from Course " + c.getName());
        }

    }
}
