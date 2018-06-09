package lk.ijse.mountCalvary.controller;

import com.jfoenix.controls.JFXTextField;
import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import java.util.HashSet;
import java.util.Set;

public class AutoComplete<T>{
    private JFXTextField teaxtField;
    private  Set<T> autoCompletions;
    private SuggestionProvider<T> provider;
    private ObservableList<T> resultSet;
    private AutoCompletionTextFieldBinding<T> tAutoCompletionTextFieldBinding;

    public AutoComplete(JFXTextField textField) {
        autoCompletions = new HashSet<>();
        provider = SuggestionProvider.create(autoCompletions);
        tAutoCompletionTextFieldBinding = new AutoCompletionTextFieldBinding<>(textField, provider);
    }
    public AutoComplete(JFXTextField textField, ObservableList<T> resultSet) {
        this.resultSet = resultSet;
        autoCompletions = new HashSet<>(resultSet);
        provider = SuggestionProvider.create(autoCompletions);
        tAutoCompletionTextFieldBinding = new AutoCompletionTextFieldBinding<>(textField, provider);
    }
    public void changeSuggestion(ObservableList<T> list){
        provider.clearSuggestions();
        autoCompletions = new HashSet<>(list);
        provider.addPossibleSuggestions(autoCompletions);
    }
    public void setAutoCompletionsAction(EventHandler<AutoCompletionBinding.AutoCompletionEvent<T>> e){
        tAutoCompletionTextFieldBinding.setOnAutoCompleted( e);
    }
}
