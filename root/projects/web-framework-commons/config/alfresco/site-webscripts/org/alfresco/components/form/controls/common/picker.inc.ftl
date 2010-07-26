<#assign compactMode = field.control.params.compactMode!false>

<#assign fieldValue = "">
<#if field.control.params.currentValueContextProperty??>
   <#if context.properties[field.control.params.currentValueContextProperty]??>
      <#assign fieldValue = context.properties[field.control.params.currentValueContextProperty]>
   <#elseif args[field.control.params.currentValueContextProperty]??>
      <#assign fieldValue = args[field.control.params.currentValueContextProperty]>
   </#if>
<#elseif context.properties[field.name]??>
   <#assign fieldValue = context.properties[field.name]>
<#else>
   <#assign fieldValue = field.value>
</#if>

<#macro renderPickerJS field picker="picker">
   var ${picker} = new Alfresco.ObjectFinder("${controlId}", "${fieldHtmlId}").setOptions(
   {
      <#if form.mode == "view" || field.disabled>disabled: true,</#if>
      compactMode: ${compactMode?string},
   <#if field.mandatory??>
      mandatory: ${field.mandatory?string},
   <#elseif field.endpointMandatory??>
      mandatory: ${field.endpointMandatory?string},
   </#if>
      currentValue: "${fieldValue}",
      minSearchTermLength: ${args.minSearchTermLength!'1'},
      maxSearchResults: ${args.maxSearchResults!'100'}
   }).setMessages(
      ${messages}
   );
</#macro>

<#macro renderPickerHTML controlId>
   <#assign pickerId = controlId + "-picker">
<div id="${pickerId}" class="picker yui-panel">
   <div id="${pickerId}-head" class="hd">${msg("form.control.object-picker.header")}</div>

   <div id="${pickerId}-body" class="bd">
      <div class="picker-header">
         <div id="${pickerId}-folderUpContainer" class="folder-up"><button id="${pickerId}-folderUp"></button></div>
         <div id="${pickerId}-navigatorContainer" class="navigator">
            <button id="${pickerId}-navigator"></button>
            <div id="${pickerId}-navigatorMenu" class="yuimenu">
               <div class="bd">
                  <ul id="${pickerId}-navigatorItems" class="navigator-items-list">
                     <li>&nbsp;</li>
                  </ul>
               </div>
            </div>
         </div>
         <div id="${pickerId}-searchContainer" class="search">
            <input type="text" class="search-input" name="-" id="${pickerId}-searchText" value="" maxlength="256" />
            <span class="search-button"><button id="${pickerId}-searchButton">${msg("form.control.object-picker.search")}</button></span>
         </div>
      </div>
      <div class="yui-g">
         <div id="${pickerId}-left" class="yui-u first panel-left">
            <div id="${pickerId}-results" class="picker-items">
               <#nested>
            </div>
         </div>
         <div id="${pickerId}-right" class="yui-u panel-right">
            <div id="${pickerId}-selectedItems" class="picker-items"></div>
         </div>
      </div>
      <div class="bdft">
         <button id="${controlId}-ok" tabindex="0">${msg("button.ok")}</button>
         <button id="${controlId}-cancel" tabindex="0">${msg("button.cancel")}</button>
      </div>
   </div>

</div>
</#macro>
