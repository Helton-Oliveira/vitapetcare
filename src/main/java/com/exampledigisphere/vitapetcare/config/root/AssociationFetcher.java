package com.exampledigisphere.vitapetcare.config.root;

@Info(
        dev = Info.Dev.heltonOliveira,
        label = Info.Label.doc,
        date = "29/12/2025",
        description = "Interface para busca de associações"
)
public interface AssociationFetcher {
    String getPropertyName();
}
