package elca.ntig.partnerapp.fe.factory;

import elca.ntig.partnerapp.fe.common.enums.Language;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Credit: https://stackoverflow.com/questions/32464974/javafx-change-application-language-on-the-run
 */
public class ObservableResourceFactory {
    public final static String RESOURCE_BUNDLE_NAME = "bundles/languageBundle";
    private ObjectProperty<ResourceBundle> resource = new SimpleObjectProperty<>();

    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }

    public final void setResource(Language language) {
        resourcesProperty().set(ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, language.getLocale()));
    }

    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resource;
    }

    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            {
                bind(resourcesProperty());
            }

            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }


}
