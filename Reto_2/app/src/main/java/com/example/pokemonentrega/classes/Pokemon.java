package com.example.pokemonentrega.classes;

public class Pokemon {
   private Form[] forms;
   private StatsContainer[] stats;
   private TypeContainer[] types;
   private Sprite sprites;

   public Pokemon() {
   }

   public Pokemon(Form[] forms, StatsContainer[] stats, TypeContainer[] types, Sprite sprites) {
      this.forms = forms;
      this.stats = stats;
      this.types = types;
      this.sprites = sprites;
   }

   public Form[] getForms() {
      return forms;
   }

   public void setForms(Form[] forms) {
      this.forms = forms;
   }

   public StatsContainer[] getStats() {
      return stats;
   }

   public void setStats(StatsContainer[] stats) {
      this.stats = stats;
   }

   public TypeContainer[] getTypes() {
      return types;
   }

   public void setTypes(TypeContainer[] types) {
      this.types = types;
   }

   public Sprite getSprites() {
      return sprites;
   }

   public void setSprites(Sprite sprites) {
      this.sprites = sprites;
   }
}
