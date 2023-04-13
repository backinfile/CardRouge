package ${package};

import com.backinfile.cardRouge.cardView.CardView;
import com.backinfile.cardRouge.cardView.CardViewBaseMod;
<#list imports as import>
import ${import};
</#list>

/**
 * 此类是自动生成的，不要修改
 */
public interface CardViewModulesInterface {
    CardViewModules getModules();

<#list classes as name>
    default ${name} get${name}() {
        return getModules().get${name}();
    }
</#list>
}
