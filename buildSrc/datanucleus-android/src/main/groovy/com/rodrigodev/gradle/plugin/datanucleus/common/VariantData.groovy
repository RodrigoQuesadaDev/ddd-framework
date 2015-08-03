package com.rodrigodev.gradle.plugin.datanucleus.common

class VariantData {

    def variant

    String getSuffix() {
        return variant.name.capitalize() + variant.flavorName.capitalize()
    }
}