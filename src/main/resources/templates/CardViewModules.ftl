package ${package};

import com.backinfile.cardRouge.cardView.CardView;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
<#list imports as import>
import ${import};
</#list>

/**
 * 此类是自动生成的，不要修改
 */
public class CardViewModules {
    private final CardView cardView;
    private CardViewBaseMod[] modules;

    public CardViewModules(CardView cardView) {
        this.cardView = cardView;
    }


    public void init() {
        modules = new CardViewBaseMod[E.values().length];
<#list classes as name>
        modules[E.${name}.ordinal()] = new ${name}(cardView);
</#list>
    }

    public void onCreate() {
        for (var mod : modules) {
            mod.onCreate();
        }
    }

    public void onShapeChange() {
        for (var mod : modules) {
            mod.onShapeChange();
        }
    }

    public void update(double delta) {
        for (var mod : modules) {
            mod.update(delta);
        }
    }

<#list classes as name>
    public ${name} get${name}() {
        return (${name}) modules[E.${name}.ordinal()];
    }
</#list>

    private enum E {
<#list classes as name>
        ${name},
</#list>
    }
}
