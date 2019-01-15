package cooking.book.dbseeder;

import cooking.book.model.recipe.Ingredient;
import cooking.book.model.recipe.Recipe;
import cooking.book.model.recipe.RecipeCategory;
import cooking.book.model.user.Role;
import cooking.book.model.user.RoleName;
import cooking.book.repository.recipe.RecipeRepository;
import cooking.book.repository.user.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;


@Component
public class DatabaseSeeder implements CommandLineRunner {

    private String[] recipeNames = {
            "Pizza dough",
            "Tomato sauce",
            "Whitebait & dill mayo",
            "Squash & goat's cheese bruschetta",
            "Roast goose",
            "Blue cheese & apple burger",
            "Mushroom bourguignon",
            "Meatball sub",
            "Sausage pasta bake",
            "Family pasta",
            "Roast chicken & crispy bread salad",
            "Italian spring bean salad",
            "Chocolate brownie",
            "Grilled strawberries with Pimm's",
            "Chocolate rye cookies",
            "Devil's double choc malt cookies",
            "Pineapple carpaccio",
            "Chicken & black bean chowder",
            "Minestrone soup",
            "Asian vegetable broth"
    };

    private RecipeCategory[] recipeCategories = {
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.STARTER,
            RecipeCategory.STARTER,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.STARTER,
            RecipeCategory.STARTER,
            RecipeCategory.DESSERT,
            RecipeCategory.DESSERT,
            RecipeCategory.DESSERT,
            RecipeCategory.DESSERT,
            RecipeCategory.DESSERT,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE,
            RecipeCategory.MAIN_COURSE
    };

    private String[][] ingredientNames = {
            {"strong white bread flour", "fine ground semolina flour or strong white bread flour", "tablespoon fine sea salt", "sachets of dried yeast", "golden caster sugar"},
            {"garlic", "fresh basil", "olive oil", "tin of quality plum tomatoes"},
            {"fennel seeds", "plain flour", "whitebait, from sustainable sources", "fresh dill", "free-range mayonnaise", "lemon"},
            {"butternut squash", "garlic", "sage", "chilli flakes", "olive oil", "rye bread", "soft goat's cheese", "extra virgin olive oil"},
            {"goose, halved lengthways by your butcher", "ginger", "cinnamon", "star anise", "whole cloves", "olive oil", "oranges", "red wine vinegar"},
            {"quality minced chuck steak(preferably 30% fat)", "soft round lettuce", "cress", "Braeburn or Cox apples", "blue cheese", "olive oil", "burger buns", "american mustard"},
            {"shallots", "dried porcini mushrooms", "portobello mushrooms", "shiitake mushrooms", "chestnut mushrooms", "unsalted butter", "olive oil", "carrots", "garlic", "fresh thyme", "fresh bay", "red wine", "tomato puree"},
            {"olive oil", "potato", "minced higher-welfare pork shoulder", "quality minced beef", "fresh rosemary", "submarine rolls", "red wine vinegar", "Red Leicester cheese", "watercress", "red onions", "fennel", "plain flour", "porter or stout", "quality organic chicken stock", "HP sauce", "tomato ketchup", "Worcestershire sauce", "English mustard", "mango chutney"},
            {"garlic", "mature Cheddar cheese", "stale bread", "dried oregano", "olive oil", "higher-welfare sausages", "dried chilli flakes", "tins of quality plum tomatoes", "dried rigatoni"},
            {"durum wheat flour", "ripe tomatoes", "garlic", "olive oil", "peperoni cruschi(dried Senise peppers)", "quality breadcrumbs", "extra virgin olive oil"},
            {"free-range chicken thighs, skin on, bone out", "olive oil", "lemon", "garlic", "fresh thyme", "ciabatta", "pine nuts", "etra virgin olive oil", "runny honey", "raisins", "rocket"},
            {"celery", "carrot", "radicchio", "fresh basil", "tin of beans(borlotti or kidney)", "chopped onion", "extra virgin olive oil", "white wine vinegar", "Parmesan rinds", "stale bread"},
            {"quality dark chocolate(70%)", "unsalted butter", "large free-range eggs", "golden caster sugar", "self-raising flour", "quality coca powder"},
            {"strawberries", "vanilla sugar", "stem ginger, in syrup", "Pimm's", "quality vanilla ice cream"},
            {"quality dark chocolate(70%)", "unsalted butter", "rye bread", "large free-range eggs", "golden caster sugar"},
            {"unsalted butter", "quality dark chocolate(70%)", "tin of condensed milk", "ground almonds", "Horlicks", "self-raising flour", "Malteasers", "quality white chocolate"},
            {"fresh mint", "ripe pineapple", "blueberries", "Greek-style coconut yoghurt", "lime"},
            {"fresh coriander", "onions", "garlic", "celery", "olive oil", "ground cumin", "cayenne pepper", "tin of black beans", "organic chicken stock", "corn on the cob", "bay leaf", "free-range cooked chicken", "single cream", "corn tortillas", "lemon"},
            {"garlic", "red onion", "carrots", "celery", "courgette", "leek", "potato", "tin of cannellini beans", "higher-welfare smoked streaky bacon", "olive oil", "dried oregano", "fresh bay leaf", "tins of plum tomatoes", "organic vegetable stock", "seasonal greens, such as savoy cabbage, curly kale, chard", "wholemeal pasta", "fresh basil(optional)", "Parmesan cheese"},
            {"garlic", "ginger", "mixed Asian greens, such as baby pak choi, choy sum, Chinese cabbage", "spring onions", "fresh red chilli", "fresh Thai basil", "lemongrass", "star anise", "clear organic vegetable stock", "fish sauce(optional)", "soy sauce", "shiso cress", "lime"}
    };

    private String[][] ingredientQuantities = {
            {"800 g", "200 g", "1 level", "2x7 g", "1 tablespoon"},
            {"1 clove", "1 bunch", "", "1x400 g"},
            {"2 teaspoons", "50 g", "450 g", "a few sprigs", "2 tablespoons", "1"},
            {"1 small", "6 cloves", "3 sprigs", "1 teaspoon", "", "1 small", "125 g", ""},
            {"1 large", "6 cm piece", "6 large sticks", "6", "2 teaspoons", "", "2", ""},
            {"750 g", "1", "1 punnet", "2", "120 g", "", "6", ""},
            {"12", "25 g", "4", "120 g", "200 g", "25 g", "", "2 large", "2 cloves", "6 sprigs", "2 leaves", "500 ml", "1 tablespoon"},
            {"", "1 small", "500 g", "500 g", "4 sprigs", "6", "", "100 g", "50 g", "2", "1 bulb", "1 heaped tablespoon", "100 ml", "1 litre", "1 tablespoon", "1 tablespoon", "1 tablespoon", "1 teaspoon", "1 tablespoon"},
            {"3 cloves", "50 g", "50 g", "2 teaspoons", "", "4", "1 pinch", "3 x 400 g", "400 g"},
            {"400 g, plus extra for dusting", "1 kg", "6", "", "2", "100 g", ""},
            {"6", "", "1", "4 cloves", "a few sprigs", "150 g", "50 g", "", "1 tablespoon", "25 g", "90 g"},
            {"1 stick(60 g)", "half(60 g)", "120 g", "10 g", "1 and a half x 400 g", "1 tablespoon", "130 ml", "80 ml", "75 g(about 3)", "100 g"},
            {"250 g", "250 g", "4", "250 g", "2 heaped tablespoons", "2 heaped tablespoons"},
            {"400 g", "1 tablespoon", "1 tablespoon", "1 splash", "4 scoops"},
            {"100 g", "100 g", "100 g", "2", "50 g"},
            {"50 g", "200 g", "1 x 396 g", "25 g", "2 heaped teaspoons", "200 g", "100 g", "50 g"},
            {"1 bunch(30 g)", "1", "100 g", "4 tablespoons", "1"},
            {"half a bunch", "2", "2 cloves", "2 stalks", "", "1 teaspoon", "1 pinch", "1 x 400 g", "750 ml", "2", "1", "250 g", "75 ml", "2", "1"},
            {"1 clove", "1", "2", "2 sticks", "1", "1 small", "1 large", "1 x 400 g", "2 rashers", "", "half teaspoon", "1", "2 x 400 g", "1 litre", "1 large", "100 g", "half a bunch", ""},
            {"3 cloves", "5 cm piece", "200 g", "2", "1", "5 sprigs", "1 stick", "2", "800 ml", "1 teaspoon", "1 teaspoon", "1 small punnet", "1"}
    };

    private String[] instructions = {
            "For the dough, pile the flours and 1 level teaspoon of sea salt onto a clean surface and make a well in the centre.\n" +
                    "Add the yeast and sugar to 650ml lukewarm water, mix together and leave for a few minutes, then pour into the well.\n" +
                    "Using a fork and a circular movement, slowly bring in the flour from the inner edge of the well and mix into the water. Continue to mix, bringing in all the flour – when the dough comes together and becomes too hard to mix with your fork, flour your hands and begin to pat it into a ball.\n" +
                    "Knead the dough by rolling it backwards and forwards, using your hands to stretch, pull and push the dough. Keep kneading for 10 minutes, or until you have a smooth, springy, soft dough.\n" +
                    "Place the dough in a lightly greased bowl, cover with clingfilm and leave in a warm place to to prove for 45 minutes, or until doubled in size.",
            "For the sauce, peel and finely slice the garlic, then pick the basil leaves and finely chop the stalks.\n" +
                    "Heat 1 tablespoon of oil in a pan on a medium-low heat, add the garlic and basil stalks, then cook gently for a couple of minutes, or until the garlic is lightly golden, then add most of the basil leaves, the tomatoes, and a pinch of salt and pepper.\n" +
                    "Leave the sauce to tick away for around 20 minutes, or until smooth, breaking up the tomatoes up with a wooden spoon. When the time’s up, taste, and season to perfection.",
            "Heat the oil in a deep fryer or pan to 200ºC.\n" +
                    "Crush the fennel seeds in a pestle and mortar, then combine with the flour and season.\n" +
                    "Working in batches, coat the fish in the flour mixture then very carefully fry for 3 minutes, or until golden and cooked. Remove with a slotted spoon and drain on kitchen paper.\n" +
                    "Pick and finely chop the dill, then combine with the mayonnaise, and season.",
            "Preheat the oven to 200ºC/gas 6.\n" +
                    "Halve the squash lengthways, remove the seeds and cut into 8 wedges. Place in a roasting tray.\n" +
                    "Smash the garlic and add to the tray, pick in the sage leaves, then add the chilli flakes, a drizzle of olive oil and a pinch of sea salt and black pepper.\n" +
                    "Toss the wedges well, spread out evenly and roast for 35 to 40 minutes, or until golden. Remove from the oven and cool.\n" +
                    "Slice up the bread and toast on a griddle pan for 1 minute on each side. Remove to a board and spread each slice with the peeled, roasted garlic.\n" +
                    "Scoop the squash flesh into a bowl (discard the skins) and lightly mash with a fork. Spread onto the bread slices and top with the cheese, some sage leaves and a drizzle of extra virgin olive oil.",
            "Get your meat out of the fridge and up to room temperature before you cook it. Preheat the oven to 180°C/350°F/gas 4.\n" +
                    "Peel and finely slice the ginger, then, keeping everything quite coarse, lightly crush it in a pestle and mortar with the cinnamon sticks, star anise, cloves and a good pinch of sea salt and black pepper.\n" +
                    "Rub into the skin of the goose halves, then put both halves skin side up in your biggest deep-sided roasting tray and drizzle with a little oil.\n" +
                    "Roast for 3 hours (depending on the size of your goose), basting every hour. After the goose has been in for 2 hours, slice the oranges and carefully add to the tray. The goose is cooked when the leg meat falls easily off the bone.",
            "Make the burgers at least an hour before you want to cook them. Divide the mince into 4 portions and work each ball in your hands for a few minutes to melt the fat and mould them into a relatively smooth, round patty. Make them slightly bigger than your bun, as they will shrink when cooked.\n" +
                    "Pop them on a tray, cover with clingfilm and chill in the fridge.\n" +
                    "When you're ready to cook your burgers, get your toppings ready. Pick off, wash and spin dry the lettuce leaves, cut your cress, slice the apples, crumble the blue cheese into chunks, and leave everything to one side.\n" +
                    "Preheat your grill to high. Pop a large non-stick frying pan over a medium heat and add a drizzle of oil to the pan.\n" +
                    "Fry the burgers for around 4 minutes on each side if you like them pink in the middle, or longer if you prefer them fully cooked, seasoning the patties with black pepper as you cook them.\n" +
                    "Halve and toast the buns under the grill or on a hot griddle, then line them up on a board ready to go.\n" +
                    "When the burgers are cooked, top each with the blue cheese and pop under the grill for a couple of minutes until nice and oozy.",
            "Put the shallots in a bowl and cover with hot water (this makes them easy to peel). Place the dried porcini in another bowl and cover with 150ml of boiling water, then set aside.\n" +
                    "Roughly chop the portobello mushrooms and halve any larger shiitake and chestnut mushrooms, leaving the small ones whole. Heat half \n" +
                    "of the butter with 1 tablespoon oil in a casserole pan over a medium heat. Fry the mushrooms in batches, until coloured but still firm, adding another tablespoon of oil between each batch. Tip the mushrooms into a bowl and set aside.\n" +
                    "Heat the remaining butter in the pan, peel the shallots, halving any larger ones, peel and cut the carrots into 2cm slices and fry for 8 minutes, or until the veg gets some colour, stirring occasionally. Peel and chop the garlic and add for the final 2 minutes.\n" +
                    "Add the thyme, bay and wine. Strain in the porcini liquid into the pan, roughly chop the porcini and add to the pan along with the tomato purée, then simmer for 25 minutes, or until the wine has reduced slightly and the veg are cooked through. Season to taste and fish out the thyme stalks and bay leaves.\n" +
                    "Stir the cooked mushrooms into the sauce along with any juices, heating through for a couple of minutes. Season and serve. Nice with some creamy mash on the side.",
            "Preheat the oven to 200ºC/400ºF/gas 6. Lightly oil a large roasting tray.\n" +
                    "Peel and coarsely grate the potato, then place in a bowl with the minced pork and beef. Scrunch together, then use wet hands to divide and roll the mixture into 21 balls, each slightly larger than a golf ball. Reserving 3, place the rest in the prepared roasting tray and set aside.\n" +
                    "To make the gravy, peel the onions, trim the fennel, then finely chop both and place in a large pan on a medium heat with 1 tablespoon of oil. Cook for 10 minutes, or until softened, stirring occasionally.\n" +
                    "Break in the 3 reserved meatballs, then cook for a further 10 minutes on a high heat, or until dark golden. Stir in the flour for 2 minutes, then add the porter and leave to cook away.\n" +
                    "Pour in the stock, stir in the remaining gravy ingredients, then bring to the boil. Reduce to a simmer for 20 minutes, or until thick and glossy.\n" +
                    "Meanwhile, season the tray of meatballs with sea salt and black pepper, then cook in the oven for 20 to 25 minutes, or until golden and cooked through.\n" +
                    "Transfer the tray to a medium heat on the hob, pour over the gravy, add the rosemary sprigs and simmer for a few minutes while you warm the rolls in the cooling oven.\n" +
                    "Stir a splash of vinegar into the gravy, then grate over the cheese and turn off the heat.",
            "Preheat the oven to 200ºC/400ºF/gas 6.\n" +
                    "Peel and finely slice the garlic. Coarsely grate the Cheddar. Tear the stale bread into small chunks or blitz in the food processor.\n" +
                    "Tip the breadcrumbs into a bowl with one of the sliced garlic cloves, a good pinch of the oregano, the grated cheese and a pinch of sea salt and a sprinkling of black pepper. Drizzle with a little oil and toss to mix it all together.\n" +
                    "Pop the sausages out of their skins and break each one into 4 equal pieces. Roll each piece into a ball shape and place in an ovenproof dish. Drizzle over a little oil and roll to coat. Bake in the oven for about 10 minutes, or until golden and cooked through.\n" +
                    "Heat a little oil in a frying pan over a medium heat. Add the remaining sliced garlic and oregano, and the chilli flakes. Stir until the garlic is coloured, then tip in the tomatoes.\n" +
                    "Add a pinch of salt and pepper, bring to the boil, then reduce the heat and simmer for 10 minutes, stirring occasionally.\n" +
                    "Cook the rigatoni according to the packet instructions, taking it off the heat 2 minutes early, so it’s al dente. Drain in a colander and tip into a large baking dish.\n" +
                    "Add the sausage balls, pour over the sauce and toss well. Sprinkle over the cheesy crumb mix and bake for 15 minutes, or until golden on top and bubbling.",
            "Place the flour in a large bowl, make a well in the centre and gradually stir in 200ml of tepid water with a fork until combined. Switch to your hands and knead for 10 minutes, or until pliable and smooth, but not sticky (adding a little extra flour if needed). Cover and rest for 30 minutes.\n" +
                    "Divide the dough into quarters (approximately 50g chunks), then roll each chunk into a long snake and cut into 1-inch nuggets (about 2½cm). Roll each piece into a mini sausage, and then, using three fingers, press down firmly and drag the dough towards you to make three clear indentations, with pointed ends. Set aside each piece of pasta on a flour dusted tray.\n" +
                    "Blanch the tomatoes in a large pan of boiling water for 45 seconds. Carefully remove with a slotted spoon to a bowl of ice-cold water, then peel and discard the skins, and roughly chop.\n" +
                    "Peel and finely slice 4 cloves of garlic and place in a large non-stick frying pan with 3 tablespoons of olive oil. Place over a medium heat and cook until fragrant but not coloured.\n" +
                    "Add the chopped tomatoes, bring to a simmer and leave to cook for 45 minutes, or until reduced and delicious. Season to perfection with sea salt and black pepper.\n" +
                    "For the pangrattato, halve and deseed the dried peppers and place in a non-stick frying pan on a medium heat with 3 tablespoons of olive oil. Cook for 5 minutes, or until softened, then remove to a chopping board and finely chop.\n" +
                    "Peel and finely chop the remaining garlic, then fry in the hot chilli oil for 1 to 2 minutes.\n" +
                    "Tip in the breadcrumbs and fry for 1 to 2 minutes more until golden and crisp, then add the chopped peppers and stir to combine.\n" +
                    "Cook the pasta in a large pan of salted boiling water for 3 minutes, or until al dente. Spoon the pasta into the sauce, adding a good splash of cooking water to loosen and a drizzle of extra virgin olive oil.",
            "Preheat the oven to 200ºC/400ºF/gas 6.\n" +
                    "Put the chicken in a baking dish and rub with a little olive oil. Quarter the lemon and arrange in the dish with the unpeeled garlic. Tuck the thyme around the chicken, season well with sea salt and black pepper and roast for 30 minutes.\n" +
                    "Remove from the oven, then pick out and reserve the lemon and garlic. Tear the ciabatta into bite-sized pieces and add, tossing to coat in the juices, then scatter over the pine nuts.\n" +
                    "Return the dish to the oven, reduce the temperature to 180ºC/350ºF/gas 4 and cook for 10 minutes, stirring halfway through.\n" +
                    "Meanwhile, in a small bowl mix 2 tablespoons of extra virgin olive oil with the honey, then squeeze in the soft, sweet garlic (discarding the skins) and the juice from the roasted lemon, mashing as you go, until combined. Season to taste.",
            "Trim the celery, peel and trim the carrot, then cut, along with the radicchio, into 5mm cubes. Pick and finely chop the basil leaves.\n" +
                    "Drain and rinse the beans and place in a bowl (save the remainder for another dish) with the radicchio, celery, carrot, onion and basil. Stir in the olive oil and vinegar, then season and leave to rest for 2 hours at room temperature.\n" +
                    "Meanwhile, place the Parmesan rinds in a pot with 1.5 litres of cold water and cook slowly over a low–medium for 1 hour. Remove from the heat and leave the rinds in the water to cool.\n" +
                    "Preheat the oven to 200ºC/400ºF/gas 6.\n" +
                    "Cut the bread into 1cm cubes, scatter over a baking tray and toast in the oven until golden, about 20 minutes, tossing halfway through.",
            "Preheat the oven to 180ºC/350ºF/gas 4.\n" +
                    "Tear off a large piece of greaseproof paper, scrunch it up under cold water, then unfold and use it to line a 20cm square baking tin.\n" +
                    "Snap the chocolate into a heatproof bowl, dice the butter and add with a pinch of sea salt. Melt over a pan of gently simmering water, stirring regularly, then remove from the heat and leave to cool slightly.\n" +
                    "Crack the eggs into a large bowl, then add the sugar and whisk until light, pale and fluffy.\n" +
                    "Sieve in the flour, followed by the cocoa. Whisk to combine, then fold through the melted chocolate.\n" +
                    "Spoon the mixture into the lined tin and spread it out evenly. Bake for 25 to 30 minutes, or until crisp on the outside but slightly wobbly.",
            "Preheat the grill to high.\n" +
                    "Hull the strawberries and toss with the sugar.\n" +
                    "Finely slice the ginger and stir through the strawberries with 2 tablespoons of the gingery syrup. Place in a shallow ovenproof dish.\n" +
                    "Grill the strawberries for a few minutes, until softened and hot, then add the Pimm’s and stir to scrape up all the sticky bits from the bottom of the dish.",
            "Preheat the oven to 200ºC/400ºF/gas 6. Line two trays with greaseproof paper and rub with olive oil.\n" +
                    "Melt the chocolate in a heatproof bowl over a pan of gently simmering water, then remove and stir in the butter so it melts.\n" +
                    "Tear the bread into a food processor and blitz into fine crumbs, then add the eggs and sugar, and blitz again well. With the processor still running, pour in the chocolate mixture and let it blitz until combined.\n" +
                    "Spoon the cookie mix into a large sandwich bag, snip off the corner and pipe 3-4cm blobs to make 24 cookies on the lined trays.\n" +
                    "Bake for 8 to 10 minutes, or until spread and set. Sprinkle with sea salt, leave to cool a little, and tuck in.",
            "Melt the butter and chocolate in a pan on a low heat until smooth and combined, stirring occasionally.\n" +
                    "Remove from the heat and stir in the condensed milk, followed by the almonds and Horlicks.\n" +
                    "Sift in the flour and a pinch of sea salt, mix together, then chill in the fridge for 20 to 30 minutes (no longer).\n" +
                    "Once cool, but still pliable, smash up the Maltesers and roughly chop the white chocolate, then mix it all together.\n" +
                    "Preheat the oven to 170°C/325°F/gas 3.\n" +
                    "Divide the mixture into 24 equal-sized balls and place on a couple of large baking trays lined with greaseproof paper.\n" +
                    "Flatten each a little – like squashed golf balls (you can freeze them at this stage to bake another day if you like) – then bake for around 12 minutes, or until chewy in the middle and firm at the edges. Leave to sit in the tray for 5 minutes, then transfer to a wire rack to cool.",
            "Pick the mint leaves into a pestle and mortar, reserving a small handful of leaves to one side. Pound the rest into a paste, then muddle in 1 to 2 tablespoons of extra virgin olive oil to make a mint oil. Top and tail the pineapple, then slice off the skin. Quarter lengthways, remove the core, then finely slice lengthways. Arrange on a large platter or divide between your plates. Take the time to halve the blueberries, then sprinkle over the top.",
            "Pick the coriander leaves and set to one side, then finely slice the stalks. Peel and finely chop the onions and garlic, then finely slice the celery.\n" +
                    "Place a large saucepan over a medium-low heat and pour in a good lug of oil. Add the onion, garlic, celery and coriander stalks, then sauté for about 10 minutes, or until the veg is softened but not coloured.\n" +
                    "Add the cumin and cayenne to the pan and fry for a further minute. Stir in the black beans, along with their liquid, and the chicken stock.\n" +
                    "Cut the corn from the cob and add it all to the pan, along with the bay leaf. Gently bring it all to the boil, then reduce the heat to low and let it simmer for 15 minutes.\n" +
                    "If you prefer your chowder to be on the smooth side, you could blitz it just a little with a stick blender, otherwise just leave it nice and chunky.\n" +
                    "Shred the chicken and add it to the pan with the cream. Season with sea salt and black pepper to taste, then return to the hob for a further 10 minutes.\n" +
                    "Toast the corn tortillas in a hot, dry frying pan until lightly golden. Cut them into large pieces and serve them wedged into bowls of the soup, finished with the reserved coriander leaves and wedges of lemon for squeezing over.",
            "Peel and finely chop the garlic and onion. Trim and roughly chop the carrots, celery and courgette, then add the vegetables to a large bowl.\n" +
                    "Cut the ends off the leek, quarter it lengthways, wash it under running water, then cut into 1cm slices. Add to the bowl.\n" +
                    "Scrub and dice the potato. Drain the cannellini beans, then set aside.\n" +
                    "Finely slice the bacon.\n" +
                    "Heat 2 tablespoons of oil in a large saucepan over a medium heat. Add the bacon and fry gently for 2 minutes, or until golden.\n" +
                    "Add the garlic, onion, carrots, celery, courgette, leek, oregano and bay and cook slowly for about 15 minutes, or until the vegetables have softened, stirring occasionally.\n" +
                    "Add the potato, cannellini beans and plum tomatoes, then pour in the vegetable stock. Stir well, breaking up the tomatoes with the back of a spoon.\n" +
                    "Cover with a lid and bring everything slowly to the boil, then simmer for about 30 minutes, or until the potato is cooked through. Meanwhile...\n" +
                    "Remove and discard any tough stalks bits from the greens, then roughly chop.\n" +
                    "Using a rolling pin, bash the pasta into pieces while it’s still in the packet or wrap in a clean tea towel.\n" +
                    "To check the potato is cooked, pierce a chunk of it with a sharp knife – if it pierces easily, it’s done.\n" +
                    "Add the greens and pasta to the pan, and cook for a further 10 minutes, or until the pasta is al dente. This translates as ‘to the tooth’ and means that it should be soft enough to eat, but still have a bit of a bite and firmness to it. Try some just before the time is up to make sure you cook it perfectly.\n" +
                    "Add a splash more stock or water to loosen, if needed.\n" +
                    "Pick over the basil leaves (if using) and stir through. Season to taste with sea salt and black pepper, then serve with a grating of Parmesan and a slice of wholemeal bread, if you like.",
            "Peel and crush the garlic, then peel and roughly chop the ginger. Trim the greens, finely shredding the cabbage, if using. Trim and finely slice the spring onions and chilli. Pick the herbs.\n" +
                    "Bash the lemongrass on a chopping board with a rolling pin until it breaks open, then add to a large saucepan along with the garlic, ginger and star anise.\n" +
                    "Place the pan over a high heat, then pour in the vegetable stock. Bring it just to the boil, then turn down very low and gently simmer for 30 minutes.\n" +
                    "A couple of minutes before it’s cooked, throw in your Asian veggies and gently cook until they are wilted but still crunchy."
    };

    private String[] suggestions = {
            "To assemble the pizzas, divide the dough in half. Wrap one half in clingfilm and freeze for another day. With the remaining half, divide the dough into 4 equal balls.\n" +
                    "Flour each dough ball, then cover with clingfilm, and leave to rest for about 15 minutes – this will make them easier to roll it thinly.\n" +
                    "Dust a clean surface and the dough with a little flour or semolina, and roll it out into a rough circle, about ½cm thick.\n" +
                    "Tear off an appropriately sized piece of tin foil, rub it with olive oil, dust well with flour or semolina and place the pizza base on top. Continue doing the same with the remaining dough, dust with a little flour so you can pile them up. Cover with clingfilm and place in the fridge.\n" +
                    "When you're ready to cook them, preheat the oven to 250°C/500°F/gas 9.\n" +
                    "At this stage you can apply your topping: spread the tomato sauce over the base, spreading it out to the edges. Tear over the mozzarella and scatter with the remaining basil leaves. Drizzle with a tiny bit of olive oil and add a pinch of salt and pepper.\n" +
                    "If you can, cook the pizzas on a piece of granite in your conventional oven – if not, cook them one by one on pieces of tin foil directly on the bars of the oven shelf, towards the bottom of the oven (If you're going to cook your pizzas on the bars of the oven, make sure they're not too big – otherwise they'll be difficult to manoeuvre). Cook for 7 to 10 minutes, until the pizzas are golden and crispy.",
            "",
            "Serve the fish with dill mayo and lemon wedges.",
            "",
            "Now you’ve got two choices. Leave it to rest, covered, for 30 minutes, then serve up while it’s hot and crispy-skinned, in which case simply remove the meat to a board, shred the leg meat and slice the breast.\n" +
                    "Pour all the fat into a jar, cool, and place in the fridge for tasty cooking another day, such roast potatoes. Stir a good swig of vinegar into the tray to pick up all the sticky goodness from the base, then drizzle over your meat. Serve with spuds, veg and all the usual trimmings.\n" +
                    "Your second choice is to let everything cool in the tray, then place it in the fridge for up to 2 days, with the goose submerged and protected in its own fat, ready to reheat when you need it, getting you ahead of the game and saving you time and oven space another day.\n" +
                    "To reheat, put the whole tray back in a preheated oven at 180°C/350°F/gas 4 and let the goose crisp up for around 30 minutes, or until hot through, then shred, slice and serve as above.",
            "Now build your burgers. First layer the salad leaves and apple onto the buns, followed by a good drizzle of mustard.\n" +
                    "Pop the burgers on, and top with the cress (a cucumber and chilli relish is great here too). Squish the bun tops on and serve straight away.",
            "",
            "Slice open the rolls, then spoon in the cheesy meatballs and gravy. Top with pinches of watercress, and serve with any leftover gravy for dunking.",
            "",
            "Divide the pasta between plates and top each with a generous handful of the pangrattato. Delicious served with a leafy salad dressed with good-quality balsamic vinegar.",
            "Remove the dish from the oven, strip over the roasted thyme leaves, stir in the raisins and pour over the dressing. Cut the chicken into thick slices, then fold through the rocket, and serve straight from the dish or plate up.",
            "Divide the bean mixture between plates, then add a few tablespoons of the cooled parmesan broth. Garnish with croutons and the Parmesan rinds, finely sliced with a speed-peeler, if you like.",
            "Leave to cool in the tin for 15 minutes before slicing and serving warm – delicious with vanilla ice cream, crushed hazelnuts and caramel popcorn.",
            "Divide the strawberries and juices between four bowls, top with a scoop of ice cream and tuck in.\n" +
                    "Tips\n" +
                    "Making your own vanilla sugar couldn’t be simpler. Place 2 vanilla pods in a food processor, blitz, scrape the sides and blitz again. Add 500g caster sugar and blitz for 2 minutes, or until super-fine. Sieve the mixture and store in an airtight container – it should last for ages.",
            "",
            "If you want to take these devilish cookies to another level, either sandwich 2 cookies with a good spoonful of your favourite ice cream, or even some homemade marshmallow, and squeeze… heaven, or simply drizzle all the cookies with melted chocolate while they cool… amazing!",
            "Ripple some mint oil through the yoghurt (saving the rest for another recipe), then spoon over the fruit. Finely grate over the lime zest from a height and squeeze over the juice. Finely slice and sprinkle over the reserved mint leaves, then drizzle with a tiny bit of extra virgin olive oil (yes, you heard it – delicious).",
            "",
            "",
            "Serve the broth in deep bowls seasoned with fish sauce (if using) and soy sauce, sprinkle with the herbs, cress, spring onion and chilli, then serve with wedges of lime."
    };

    private RecipeRepository recipeRepository;

    private RoleRepository roleRepository;

    @Autowired
    public DatabaseSeeder(RecipeRepository recipeRepository, RoleRepository roleRepository) {
        this.recipeRepository = recipeRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        Iterable<Recipe> recipeOptionals = recipeRepository.findAll();
        long size = recipeOptionals.spliterator().getExactSizeIfKnown();

        if (size == 0) {
            roleRepository.save(new Role(RoleName.USER));
            roleRepository.save(new Role(RoleName.ADMIN));

            for (int i = 0; i < recipeNames.length; i++) {

                Recipe recipe = new Recipe(recipeNames[i], recipeCategories[i], instructions[i], suggestions[i]);

                for (int j = 0; j < ingredientNames[i].length; j++)
                    recipe.getIngredientsList().add(new Ingredient(ingredientNames[i][j], ingredientQuantities[i][j]));

                recipe.setLastAccessed(new Date());
                recipeRepository.save(recipe);
            }
        }
    }
}