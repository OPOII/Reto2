package com.example.pokemonentrega.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pokemonentrega.classes.RecycleWoker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.example.pokemonentrega.R;
import com.example.pokemonentrega.classes.PokeAdapter;
import com.example.pokemonentrega.classes.Pokemon;
import com.example.pokemonentrega.classes.PokemonFireStore;
import com.example.pokemonentrega.pokeapi.Constants;
import com.example.pokemonentrega.pokeapi.HTTPSWebUtilDomi;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.internal.GsonBuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lobby extends AppCompatActivity implements HTTPSWebUtilDomi.OnResponseListener, PokeAdapter.OnPokemonClickListener {
    private RecyclerView lista;
    private PokeAdapter pokeAdapter;
    private Retrofit retrofit;
    private EditText buscarEnApiPokemon;
    private EditText buscarEnStoragePokemon;
    private Button botonEnApi;
    private Button botonEnStorage;
    private HTTPSWebUtilDomi domi;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Pokemon pokemon;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private RecycleWoker worker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);
        lista = findViewById(R.id.pokeLista);
        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();
        domi = new HTTPSWebUtilDomi();
        domi.setListener(this);

        pokeAdapter = new PokeAdapter();
        pokeAdapter.setListener(this);
        lista.setAdapter(pokeAdapter);
        lista.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        lista.setLayoutManager(manager);

        //Buscar en el API
        botonEnApi = findViewById(R.id.botonAtrapar);
        buscarEnApiPokemon = findViewById(R.id.pokemonName);

        //Buscar en Firebase

        botonEnStorage = findViewById(R.id.botonBuscarPokemon);
        buscarEnStoragePokemon = findViewById(R.id.buscarPokemon);

        //worker=new RecycleWoker(this);
        // worker.start();
        ArrayList<PokemonFireStore> n = new ArrayList<>();
        Task<QuerySnapshot> dis = db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados").get().addOnCompleteListener(task -> {
            pokeAdapter.clear();
            for (QueryDocumentSnapshot doc : task.getResult()) {
                PokemonFireStore nuevo = doc.toObject(PokemonFireStore.class);
                n.add(nuevo);
                pokeAdapter.addPokemon(nuevo);
            }
        });


        botonEnApi.setOnClickListener(
                (v) -> {
                    String nombrePokemon = buscarEnApiPokemon.getText().toString().toLowerCase();
                    new Thread(() -> {
                        domi.GETrequest(Constants.SEARCH_CALLBACK, "https://pokeapi.co/api/v2/pokemon/" + nombrePokemon);
                    }).start();

                }
        );

    }

    public String retornarNombreCorrecto(String poke) {
        String retornar = "";
        String[] arreglo = {"abomasnow", "abra", "absol", "accelgor", "aegislash", "aerodactyl", "aggron", "aipom", "alakazam", "alomomola", "altaria", "amaura", "ambipom", "amoonguss", "ampharos", "anorith", "arbok", "arcanine", "arceus", "archen", "archeops", "ariados", "armaldo", "aromatisse", "aron", "articuno", "audino", "aurorus", "avalugg", "axew", "azelf", "azumarill", "azurill", "bagon", "baltoy", "banette", "barbaracle", "barboach", "basculin", "bastiodon", "bayleef", "beartic", "beautifly", "beedrill", "beheeyem", "beldum", "bellossom", "bellsprout", "bergmite", "bibarel", "bidoof", "binacle", "bisharp", "blastoise", "blaziken", "blissey", "blitzle", "boldore", "bonsly", "bouffalant", "braixen", "braviary", "breloom", "bronzong", "bronzor", "budew", "buizel", "bulbasaur", "buneary", "bunnelby", "burmy", "butterfree", "cacnea", "cacturne", "camerupt", "carbink", "carnivine", "carracosta", "carvanha", "cascoon", "castform", "caterpie", "celebi", "chandelure", "chansey", "charizard", "charmander", "charmeleon", "chatot", "cherrim", "cherubi", "chesnaught", "chespin", "chikorita", "chimchar", "chimecho", "chinchou", "chingling", "cinccino", "clamperl", "clauncher", "clawitzer", "claydol", "clefable", "clefairy", "cleffa", "cloyster", "cobalion", "cofagrigus", "combee", "combusken", "conkeldurr", "corphish", "corsola", "cottonee", "cradily", "cranidos", "crawdaunt", "cresselia", "croagunk", "crobat", "croconaw", "crustle", "cryogonal", "cubchoo", "cubone", "cyndaquil", "darkrai", "darmanitan", "darumaka", "dedenne", "deerling", "deino", "delcatty", "delibird", "delphox", "deoxys", "dewgong", "dewott", "dialga", "diancie", "diggersby", "diglett", "ditto", "dodrio", "doduo", "donphan", "doublade", "dragalge", "dragonair", "dragonite", "drapion", "dratini", "drifblim", "drifloon", "drilbur", "drowzee", "druddigon", "ducklett", "dugtrio", "dunsparce", "duosion", "durant", "dusclops", "dusknoir", "duskull", "dustox", "dwebble", "eelektrik", "eelektross", "eevee", "ekans", "electabuzz", "electivire", "electrike", "electrode", "elekid", "elgyem", "emboar", "emolga", "empoleon", "entei", "escavalier", "espeon", "espurr", "excadrill", "exeggcute", "exeggutor", "exploud", "farfetch'd", "fearow", "feebas", "fennekin", "feraligatr", "ferroseed", "ferrothorn", "finneon", "flaaffy", "flabébé", "flareon", "fletchinder", "fletchling", "floatzel", "floette", "florges", "flygon", "foongus", "forretress", "fraxure", "frillish", "froakie", "frogadier", "froslass", "furfrou", "furret", "gabite", "gallade", "galvantula", "garbodor", "garchomp", "gardevoir", "gastly", "gastrodon", "genesect", "gengar", "geodude", "gible", "gigalith", "girafarig", "giratina", "glaceon", "glalie", "glameow", "gligar", "gliscor", "gloom", "gogoat", "golbat", "goldeen", "golduck", "golem", "golett", "golurk", "goodra", "goomy", "gorebyss", "gothita", "gothitelle", "gothorita", "gourgeist", "granbull", "graveler", "greninja", "grimer", "grotle", "groudon", "grovyle", "growlithe", "grumpig", "gulpin", "gurdurr", "gyarados", "happiny", "hariyama", "haunter", "hawlucha", "haxorus", "heatmor", "heatran", "heliolisk", "helioptile", "heracross", "herdier", "hippopotas", "hippowdon", "hitmonchan", "hitmonlee", "hitmontop", "honchkrow", "honedge", "ho-oh", "hoopa", "hoothoot", "hoppip", "horsea", "houndoom", "houndour", "huntail", "hydreigon", "hypno", "igglybuff", "illumise", "infernape", "inkay", "ivysaur", "jellicent", "jigglypuff", "jirachi", "jolteon", "joltik", "jumpluff", "jynx", "kabuto", "kabutops", "kadabra", "kakuna", "kangaskhan", "karrablast", "kecleon", "keldeo", "kingdra", "kingler", "kirlia", "klang", "klefki", "klink", "klinklang", "koffing", "krabby", "kricketot", "kricketune", "krokorok", "krookodile", "kyogre", "kyurem", "lairon", "lampent", "landorus", "lanturn", "lapras", "larvesta", "larvitar", "latias", "latios", "leafeon", "leavanny", "ledian", "ledyba", "lickilicky", "lickitung", "liepard", "lileep", "lilligant", "lillipup", "linoone", "litleo", "litwick", "lombre", "lopunny", "lotad", "loudred", "lucario", "ludicolo", "lugia", "lumineon", "lunatone", "luvdisc", "luxio", "luxray", "machamp", "machoke", "machop", "magby", "magcargo", "magikarp", "magmar", "magmortar", "magnemite", "magneton", "magnezone", "makuhita", "malamar", "mamoswine", "manaphy", "mandibuzz", "manectric", "mankey", "mantine", "mantyke", "maractus", "mareep", "marill", "marowak", "marshtomp", "masquerain", "mawile", "medicham", "meditite", "meganium", "meloetta", "meowstic", "meowth", "mesprit", "metagross", "metang", "metapod", "mew", "mewtwo", "mienfoo", "mienshao", "mightyena", "milotic", "miltank", "mime jr.", "minccino", "minun", "misdreavus", "mismagius", "moltres", "monferno", "mothim", "mr. mime", "mudkip", "muk", "munchlax", "munna", "murkrow", "musharna", "natu", "nidoking", "nidoqueen", "nidoran", "nidoran♂", "nidorina", "nidorino", "nincada", "ninetales", "ninjask", "noctowl", "noibat", "noivern", "nosepass", "numel", "nuzleaf", "octillery", "oddish", "omanyte", "omastar", "onix", "oshawott", "pachirisu", "palkia", "palpitoad", "pancham", "pangoro", "panpour", "pansage", "pansear", "paras", "parasect", "patrat", "pawniard", "pelipper", "persian", "petilil", "phanpy", "phantump", "phione", "pichu", "pidgeot", "pidgeotto", "pidgey", "pidove", "pignite", "pikachu", "piloswine", "pineco", "pinsir", "piplup", "plusle", "politoed", "poliwag", "poliwhirl", "poliwrath", "ponyta", "poochyena", "porygon", "porygon2", "porygon-z", "primeape", "prinplup", "probopass", "psyduck", "pumpkaboo", "pupitar", "purrloin", "purugly", "pyroar", "quagsire", "quilava", "quilladin", "qwilfish", "raichu", "raikou", "ralts", "rampardos", "rapidash", "raticate", "rattata", "rayquaza", "regice", "regigigas", "regirock", "registeel", "relicanth", "remoraid", "reshiram", "reuniclus", "rhydon", "rhyhorn", "rhyperior", "riolu", "roggenrola", "roselia", "roserade", "rotom", "rufflet", "sableye", "salamence", "samurott", "sandile", "sandshrew", "sandslash", "sawk", "sawsbuck", "scatterbug", "sceptile", "scizor", "scolipede", "scrafty", "scraggy", "scyther", "seadra", "seaking", "sealeo", "seedot", "seel", "seismitoad", "sentret", "serperior", "servine", "seviper", "sewaddle", "sharpedo", "shaymin", "shedinja", "shelgon", "shellder", "shellos", "shelmet", "shieldon", "shiftry", "shinx", "shroomish", "shuckle", "shuppet", "sigilyph", "silcoon", "simipour", "simisage", "simisear", "skarmory", "skiddo", "skiploom", "skitty", "skorupi", "skrelp", "skuntank", "slaking", "slakoth", "sliggoo", "slowbro", "slowking", "slowpoke", "slugma", "slurpuff", "smeargle", "smoochum", "sneasel", "snivy", "snorlax", "snorunt", "snover", "snubbull", "solosis", "solrock", "spearow", "spewpa", "spheal", "spinarak", "spinda", "spiritomb", "spoink", "spritzee", "squirtle", "stantler", "staraptor", "staravia", "starly", "starmie", "staryu", "steelix", "stoutland", "stunfisk", "stunky", "sudowoodo", "suicune", "sunflora", "sunkern", "surskit", "swablu", "swadloon", "swalot", "swampert", "swanna", "swellow", "swinub", "swirlix", "swoobat", "sylveon", "taillow", "talonflame", "tangela", "tangrowth", "tauros", "teddiursa", "tentacool", "tentacruel", "tepig", "terrakion", "throh", "thundurus", "timburr", "tirtouga", "togekiss", "togepi", "togetic", "torchic", "torkoal", "tornadus", "torterra", "totodile", "toxicroak", "tranquill", "trapinch", "treecko", "trevenant", "tropius", "trubbish", "turtwig", "tympole", "tynamo", "typhlosion", "tyranitar", "tyrantrum", "tyrogue", "tyrunt", "umbreon", "unfezant", "unown", "ursaring", "uxie", "vanillish", "vanillite", "vanilluxe", "vaporeon", "venipede", "venomoth", "venonat", "venusaur", "vespiquen", "vibrava", "victini", "victreebel", "vigoroth", "vileplume", "virizion", "vivillon", "volbeat", "volcanion", "volcarona", "voltorb", "vullaby", "vulpix", "wailmer", "wailord", "walrein", "wartortle", "watchog", "weavile", "weedle", "weepinbell", "weezing", "whimsicott", "whirlipede", "whiscash", "whismur", "wigglytuff", "wingull", "wobbuffet", "woobat", "wooper", "wormadam", "wurmple", "wynaut", "xatu", "xerneas", "yamask", "yanma", "yanmega", "yveltal", "zangoose", "zapdos", "zebstrika", "zekrom", "zigzagoon", "zoroark", "zorua", "zubat", "zweilous", "zygarde"};
        ArrayList<String> pokemons = new ArrayList<>();
        for (int i = 0; i < arreglo.length; i++) {
            pokemons.add(arreglo[i]);
        }
        if (pokemons.contains(poke)) {
            retornar = "Existe";
        } else {
            retornar = "El pokemon que busca no existe, por favor ingrese un pokemon valido";
        }
        return retornar;
    }

    @Override
    public void onResponse(int callbackID, String response) {
        switch (callbackID) {
            case Constants.SEARCH_CALLBACK:

                this.runOnUiThread(() -> {
                    Gson gson = new Gson();
                    pokemon = gson.fromJson(response, Pokemon.class);
                    Map<String, Object> data = new HashMap<>();
                    data.put("Nombre", pokemon.getForms()[0].getName());
                    data.put("Defensa", pokemon.getStats()[3].getBase_stat() + "");
                    data.put("Ataque", pokemon.getStats()[1].getBase_stat() + "");
                    data.put("Velocidad", pokemon.getStats()[5].getBase_stat() + "");
                    data.put("Vida", pokemon.getStats()[0].getBase_stat() + "");
                    data.put("UrlFoto", pokemon.getSprites().getFront_default());
                    data.put("Tipo", pokemon.getTypes()[0].getType().getName());
                    if (auth.getCurrentUser() != null) {
                        db.collection("users").document(auth.getCurrentUser().getUid()).collection("pokemonsAtrapados").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Lobby.this, "Se atrapo el pokemon: " + pokemon.getForms()[0].getName() + "!! Bien hecho", Toast.LENGTH_LONG);
                            }
                        });
                    }

                });

                break;

        }
    }


    @Override
    public void onClickListener(PokemonFireStore pokmon) {
    Intent intent=new Intent(this,InfoPokemons.class);
    intent.putExtra("mypoke",  pokmon);
    startActivity(intent);
    }
}