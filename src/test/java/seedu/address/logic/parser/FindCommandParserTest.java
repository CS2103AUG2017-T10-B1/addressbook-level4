package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.DetailsContainsPredicate;
import seedu.address.model.person.FuzzySearchPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.FindDetailDescriptorBuilder;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();
    private String EXPECTED_MESSAGE = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE);

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", EXPECTED_MESSAGE);
    }

    @Test
    public void parse_invalidModePrefix_throwsParseException() {
        assertParseFailure(parser, "-a", EXPECTED_MESSAGE);
        assertParseFailure(parser, "--u", EXPECTED_MESSAGE);
        assertParseFailure(parser, "--d", EXPECTED_MESSAGE);
    }

    @Test
    public void parse_emptyModeArgs_throwsParseException() {
        assertParseFailure(parser, "-u", EXPECTED_MESSAGE);
        assertParseFailure(parser, "-d    ", EXPECTED_MESSAGE);
    }

    @Test
    public void parse_validFindFuzzy_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new FuzzySearchPredicate("Ali"));
        assertParseSuccess(parser, "-u Ali", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " -u   Ali    ", expectedFindCommand);
    }

    @Test
    public void parse_validFindInDetails_returnsFindCommand() {
        FindCommand.FindDetailDescriptor expectedDescriptor =
                new FindDetailDescriptorBuilder().withName("ali").withPhone("999").build();
        FindCommand expectedFindCommand =
                new FindCommand(new DetailsContainsPredicate(expectedDescriptor));
        assertParseSuccess(parser, "-d n/ali p/999", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "  -d     n/ ali   p/   999", expectedFindCommand);
    }

    @Test
    public void parse_validFindByName_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

}
