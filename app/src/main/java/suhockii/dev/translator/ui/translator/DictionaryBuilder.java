package suhockii.dev.translator.ui.translator;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import suhockii.dev.translator.R;
import suhockii.dev.translator.data.network.models.dictionary.Ex;
import suhockii.dev.translator.data.network.models.dictionary.Mean;
import suhockii.dev.translator.data.network.models.dictionary.Syn;
import suhockii.dev.translator.data.network.models.dictionary.Tr;
import suhockii.dev.translator.data.repositories.database.Translation;

import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;
import static android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
import static android.text.Spanned.SPAN_EXCLUSIVE_INCLUSIVE;

/**
 * Created by Maksim Sukhotski on 4/29/2017.
 */

public final class DictionaryBuilder {

    private static final String SPACE = " ";
    private static final String DOT = ".";
    private static final String COMMA = ",";

    public static CharSequence makeDef(Translation response) {
        if (response.getDictionaryObject().getDef() != null) {
            final SpannableStringBuilder formattedDefinition = new SpannableStringBuilder();
            formattedDefinition.append(response.getDictionaryObject().getDef().get(0).getText());
            if (response.getDictionaryObject().getDef().get(0).getTs() != null &&
                    !response.getDictionaryObject().getDef().get(0).getTs().isEmpty()) {
                final int transcStart = formattedDefinition.length();
                formattedDefinition
                        .append(SPACE)
                        .append("[")
                        .append(response.getDictionaryObject().getDef().get(0).getTs())
                        .append("]");
                formattedDefinition.setSpan(new ForegroundColorSpan(Color.BLACK),
                        transcStart,
                        formattedDefinition.length(),
                        SPAN_EXCLUSIVE_INCLUSIVE);
            }
            return formattedDefinition;
        }
        return "";
    }

    static void makeTranslations(LinearLayout parent, LayoutInflater inflater,
                                 List<Tr> translations) {
        int translationCount = 0;
        for (final Tr translation : translations) {
            translationCount++;
            String count = Integer.toString(translationCount);
            final TextView textViewDefinitions =
                    (TextView) inflater.inflate(R.layout.part_dict_definition, null);
            parent.addView(textViewDefinitions);
            final SpannableStringBuilder stringBuilderDefinitions = new SpannableStringBuilder();
            stringBuilderDefinitions.append(count).append(DOT).append(SPACE);
            stringBuilderDefinitions.setSpan(new StyleSpan(BOLD),
                    0,
                    count.length() + DOT.length(),
                    SPAN_EXCLUSIVE_EXCLUSIVE);
            if (translation.getText() != null) {
                stringBuilderDefinitions.append(translation.getText());
                if (translation.getGen() != null) {
                    stringBuilderDefinitions.append(SPACE).append(translation.getGen());
                    final int genStart = stringBuilderDefinitions.length() - translation.getGen().length();
                    final int genEnd = stringBuilderDefinitions.length();
                    stringBuilderDefinitions.setSpan(new StyleSpan(ITALIC),
                            genStart,
                            genEnd,
                            SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilderDefinitions.setSpan(new ForegroundColorSpan(Color.BLACK),
                            genStart,
                            genEnd,
                            SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (translation.getAsp() != null) {
                    stringBuilderDefinitions.append(SPACE).append(translation.getAsp());
                    final int aspStart =
                            stringBuilderDefinitions.length() - translation.getAsp().length();
                    final int aspEnd = stringBuilderDefinitions.length();
                    stringBuilderDefinitions.setSpan(new StyleSpan(ITALIC),
                            aspStart,
                            aspEnd,
                            SPAN_EXCLUSIVE_EXCLUSIVE);
                    stringBuilderDefinitions.setSpan(new ForegroundColorSpan(Color.BLACK),
                            aspStart,
                            aspEnd,
                            SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }

            if (translation.getSyn() != null) {
                for (final Syn syn : translation.getSyn()) {
                    if (syn.getText() != null) {
                        stringBuilderDefinitions.append(COMMA).append(SPACE).append(syn.getText());
                        if (syn.getGen() != null) {
                            stringBuilderDefinitions.append(SPACE).append(syn.getGen());
                            final int genStart =
                                    stringBuilderDefinitions.length() - syn.getGen().length();
                            final int genEnd = stringBuilderDefinitions.length();
                            stringBuilderDefinitions.setSpan(new StyleSpan(ITALIC),
                                    genStart,
                                    genEnd,
                                    SPAN_EXCLUSIVE_EXCLUSIVE);
                            stringBuilderDefinitions.setSpan(new ForegroundColorSpan(Color.BLACK),
                                    genStart,
                                    genEnd,
                                    SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                        if (syn.getAsp() != null) {
                            stringBuilderDefinitions.append(SPACE).append(syn.getAsp());
                            final int aspStart =
                                    stringBuilderDefinitions.length() - syn.getAsp().length();
                            final int aspEnd = stringBuilderDefinitions.length();
                            stringBuilderDefinitions.setSpan(new StyleSpan(ITALIC),
                                    aspStart,
                                    aspEnd,
                                    SPAN_EXCLUSIVE_EXCLUSIVE);
                            stringBuilderDefinitions.setSpan(new ForegroundColorSpan(Color.BLACK),
                                    aspStart,
                                    aspEnd,
                                    SPAN_EXCLUSIVE_EXCLUSIVE);
                        }
                    }
                }
            }
            textViewDefinitions.setText(stringBuilderDefinitions);
            if (translation.getMean() != null)
                makeMeanings(parent, inflater, translation.getMean());
            if (translation.getEx() != null) makeExamples(parent, inflater, translation.getEx());
        }
    }

    private static void makeMeanings(LinearLayout parent,
                                     LayoutInflater inflater,
                                     List<Mean> meanings) {
        final TextView textViewMeaning =
                (TextView) inflater.inflate(R.layout.part_dict_meaning, null);
        parent.addView(textViewMeaning);
        final StringBuilder stringBuilderMeanings = new StringBuilder("(");
        for (final Iterator<Mean> iterator = meanings.iterator();
             iterator.hasNext(); ) {
            final Mean meaning = iterator.next();
            stringBuilderMeanings.append(meaning.getText());
            if (iterator.hasNext()) stringBuilderMeanings.append(COMMA).append(SPACE);
        }
        stringBuilderMeanings.append(")");
        textViewMeaning.setText(stringBuilderMeanings.toString());
    }

    private static void makeExamples(LinearLayout parent,
                                     LayoutInflater inflater,
                                     List<Ex> examples) {
        final SpannableStringBuilder stringBuilderExample = new SpannableStringBuilder();
        for (final Ex example : examples) {
            final TextView textViewExample =
                    (TextView) inflater.inflate(R.layout.part_dict_example, null);
            parent.addView(textViewExample);
            stringBuilderExample.clear();
            stringBuilderExample
                    .append(example.getText());
            if (example.getTr() != null)
                stringBuilderExample.append(SPACE).append("—").append(SPACE)
                        .append(example.getTr().get(0).getText());
            stringBuilderExample.setSpan(new StyleSpan(ITALIC), 0, stringBuilderExample.length(),
                    SPAN_EXCLUSIVE_EXCLUSIVE);
            textViewExample.setText(stringBuilderExample);
        }
    }
}
