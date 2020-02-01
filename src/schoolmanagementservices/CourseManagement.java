package schoolmanagementservices;

import entities.Course;
import entities.Program;
import entities.Teacher;
import integration.CourseDAO;
import view.UI;

public class CourseManagement {

    CourseDAO daoCourse;
    UI ui;
    TeacherManagement teacher;
    ProgramManagement program;

    public CourseManagement(CourseDAO courseDAOImpl, UI ui) {
        daoCourse = courseDAOImpl;
        this.ui = ui;
    }

    public void setTeacher(TeacherManagement teacher) {
        this.teacher = teacher;
    }

    public void setProgram(ProgramManagement program) {
        this.program = program;
    }

    public Course findByNameOrID() {
        String input = ui.inputString();
        if (input.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        }

        if (daoCourse.findByNameOrID(input).count() > 1) {
            daoCourse.findByNameOrID(input).forEach(c -> ui.printResult(c
                    + (c.getProgram() != null ? ". Program: " + c.getProgram().getName() : "")));
            ui.outputString("\nMore than one result.\nEnter ID of Course instead or search for another Course:");
            return findByNameOrID();
        }

        Course c = daoCourse.findByNameOrID(input).findFirst().orElse(null);

        if (c != null) {
            showDetailedInfo(c);
        } else {
            ui.outputString("No result found");
        }
        return c;
    }

    public void showDetailedInfo(Course c) {
        ui.printResult(c + (c.getProgram() != null ? ". Program: " + c.getProgram().getName() : ""));
        if (!c.getTeacherList().isEmpty()) {
            ui.outputString("\nCurrent Teachers:");
        }
        c.getTeacherList().forEach(ui::printResult);
    }

    public void showAll() {
        daoCourse.findAll().sorted((e1, e2) -> e1.getId() - e2.getId())
                .forEach(c -> ui.printResult(c
                + (c.getProgram() != null ? ". Program: " + c.getProgram().getName() : "")));
    }

    public Course add() {

        ui.outputString("Enter Course name:");
        String name = ui.inputString();
        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");
            return null;
        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Course name cannot be only numbers, try again");
            return add();
        }
        ui.outputString("Enter Course Start Date (yyyy-mm-dd):");
        String startDate = ui.inputDate();
        ui.outputString("Enter Course End Date (yyyy-mm-dd):");
        String endDate = ui.inputDate();

        Course c = new Course(name, startDate, endDate);
        daoCourse.create(c);
        ui.outputString("Course created");
        return c;
    }

    public void update(String choice) {
        ui.outputString("Enter Name or ID of Course to Update:");
        Course c = findByNameOrID();
        if (c == null) {
            if (choice.equals("addTeachers") || choice.equals("addProgram")) {
                ui.pressEnterToContinue();
            }
            return;
        }

        switch (choice) {

            case "updateName":
                updateName(c);
                break;
            case "addTeachers":
                addTeachers(c);
                break;
            case "removeTeacher":
                removeTeacher(c);
                break;
            case "addProgram":
                addProgram(c);
                break;
            case "removeProgram":
                removeProgram(c);
                break;
            case "updateStartDate":
                ui.outputString("Enter new Start Date (yyyy-mm-dd):");
                c.setStartDate(ui.inputDate());
                ui.outputString("Course Start Date updated");
                break;
            case "updateEndDate":
                ui.outputString("Enter new End Date (yyyy-mm-dd):");
                c.setEndDate(ui.inputDate());
                ui.outputString("Course End Date updated");
                break;

        }

        daoCourse.update(c);

    }

    public void updateName(Course c) {

        ui.outputString("Enter new Course name:");

        String name = ui.inputString();

        if (name.isEmpty()) {
            ui.outputString("Cancelled, no input detected");

        } else if (c.getProgram() != null && c.getProgram().getCourseList().stream().anyMatch(e -> e.getName().equalsIgnoreCase(c.getName()))) {
            ui.outputString("A Course with that name already exists in Program "
                    + c.getProgram().getName() + ", choose another name");

        } else if (name.matches("^[0-9]\\d*$")) {
            ui.outputString("Course name cannot be only numbers, try again");

        } else {
            c.setName(name);
            ui.outputString("Course Name updated");
        }
    }

    public void delete() {
        ui.outputString("Enter Name or ID of Course to Delete:");
        Course c = findByNameOrID();
        if (c != null) {
            daoCourse.delete(c);
            ui.outputString(c.getName() + " removed successfully from Courses");
        }
    }

    private void addTeachers(Course c) {
        String input = "Y";

        while (input.equalsIgnoreCase("Y")) {
            ui.outputString("\nList of available Teachers:");
            teacher.showAll();
            ui.outputString("\nEnter Name or ID of Teacher to add to Course:");
            Teacher t = teacher.findByNameOrID();

            if (t == null) {
                ui.outputString("Create new Teacher? (Y/N)");
                input = ui.inputString();
                ui.clear();
                if (input.equalsIgnoreCase("Y")) {
                    t = teacher.add();
                } else {
                    return;
                }
            }
            if (t != null) {
                if (c.getTeacherList().contains(t)) {
                    ui.outputString("\nTeacher " + t.getName() + " already added to Course " + c.getName());
                } else {
                    c.getTeacherList().add(t);
                    t.getCourseList().add(c);

                    teacher.daoTeacher.update(t);
                    ui.outputString("\nTeacher " + t.getName() + " added to Course " + c.getName());
                }
            }
            ui.outputString("Add another teacher? (Y/N)");

            input = ui.inputString();
            ui.clear();

        }
    }

    private void removeTeacher(Course c) {
        ui.outputString("Enter Name or ID of Teacher:");
        Teacher t = teacher.findByNameOrID();
        if (t != null) {
            t.getCourseList().remove(c);
            c.getTeacherList().remove(t);
            teacher.daoTeacher.update(t);
            ui.outputString("Teacher removed from Course " + c.getName());
        }

    }

    private void addProgram(Course c) {
        ui.outputString("\nList of available Programs:");
        program.showAll();
        ui.outputString("\nEnter Name or ID of Program to add/change from Course:");
        Program p = program.findByNameOrID();
        if (p == null) {
            ui.outputString("Create new Program? (Y/N)");
            String input = ui.inputString();
            ui.clear();
            if (input.equalsIgnoreCase("Y")) {
                p = program.add();
            } else {
                return;
            }

        }

        if (p != null) {
            if (p.getCourseList().stream().anyMatch(e -> e.getName().equalsIgnoreCase(c.getName()))) {
                ui.outputString("\nA Course with that name already exists in Program " + p.getName());
            } else {
                c.setProgram(p);
                p.getCourseList().add(c);
                program.daoProgram.update(p);
                ui.outputString("\nCourse " + c.getName() + " added to Program " + p.getName());
            }
        }

        ui.pressEnterToContinue();
    }

    private void removeProgram(Course c) {
        Program p = c.getProgram();
        if (p != null) {
            p.getCourseList().remove(c);
            c.setProgram(null);
            program.daoProgram.update(p);
            ui.outputString("Course removed from Program " + p.getName());
        } else {
            ui.outputString("Course is not part of a Program");
        }

    }
}
