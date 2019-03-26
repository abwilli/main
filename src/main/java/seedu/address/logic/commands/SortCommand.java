package seedu.address.logic.commands;


import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.sortMethods.SortAlphabetical;
import seedu.address.logic.commands.sortMethods.SortSkills;
import seedu.address.model.Model;
import seedu.address.model.AddressBook;
import seedu.address.model.person.*;
import seedu.address.logic.parser.SortWord;

import static java.util.Objects.requireNonNull;
import java.util.List;

/**
 * Sorts all persons in the address book and lists to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons in address book "
            + "according to the specified keyword and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD \n"
            + "Valid KEYWORD: alphabetical, skills \n"
            + "Example: " + COMMAND_WORD + " alphabetical \n"
            + "Example: " + COMMAND_WORD + " skills ";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";
    public static final String MESSAGE_NOT_SORTED = "Sorting is not successful!";

    private final SortWord method;

    private List<Person> sortedPersons;

    public SortCommand(SortWord method) {
        this.method = method;
    }

    private void ProcessCommand(Model model) {
        List<Person> lastShownList = model.getAddressBook().getPersonList();

        //Maybe use switch statement here?
        if (this.method.getSortWord().equals("alphabetical")) {
            SortAlphabetical sorted = new SortAlphabetical(lastShownList);
            sortedPersons = sorted.getList();
        }

        else if (this.method.getSortWord().equals("skills")) {
            SortSkills sorted = new SortSkills(lastShownList);
            sortedPersons = sorted.getList();
        }
        model.deleteAllPerson();
        for (Person newPerson : sortedPersons) {
            model.addPersonWithFilter(newPerson);
        }
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        try {
            model.setSortInfo(true);
            ProcessCommand(model);
            String MESSAGE_SUCCESS = "Sorted all persons by " + method.toString();
            model.commitAddressBook();
            model.setSortInfo(false);
            return new CommandResult(MESSAGE_SUCCESS);
        }
        catch(Exception e) {
            return new CommandResult(MESSAGE_NOT_SORTED);
        }
    }
}
