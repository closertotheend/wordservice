package com.wordservice.mvc;

import com.wordservice.mvc.repository.*;
import com.wordservice.mvc.service.wordfinder.WordTupleFinderService;
import com.wordservice.mvc.service.wordsaver.TextSaverService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestApplicationConfig.class})
@Transactional
public abstract class IntegrationTestsBase {

    @Autowired
    public WordTupleRepository wordTupleRepository;

    @Autowired
    public WordTriTupleRepository wordTriTupleRepository;

    @Autowired
    public Neo4jTemplate template;

    @Autowired
    public WordRepository wordRepository;

    @Autowired
    public WordRelationshipRepository wordRelationshipRepository;

    @Autowired
    public WordRelationshipTupleDAO wordRelationshipTupleDAO;

    @Autowired
    public TextSaverService textSaverService;

    @Autowired
    public WordRepositoryFixedIndexesSearch wordRepositoryFixedIndexesSearch;

    @Autowired
    public WordTupleFinderService wordTupleFinderService;

    public static final String dickensText = "I fully expected to find a Constable in the kitchen, waiting to take me up. But not only was there no Constable there, but no discovery had yet been made of the robbery. Mrs. Joe was prodigiously busy in getting the house ready for the festivities of the day, and Joe had been put upon the kitchen door-step to keep him out of the dust-pan - an article into which his destiny always led him sooner or later, when my sister was vigorously reaping the floors of her establishment.\n" +
            "\n" +
            "\"And where the deuce ha' you been?\" was Mrs. Joe's Christmas salutation, when I and my conscience showed ourselves.\n" +
            "\n" +
            "I said I had been down to hear the Carols. \"Ah! well!\" observed Mrs. Joe. \"You might ha' done worse.\" Not a doubt of that, I thought.\n" +
            "\n" +
            "\"Perhaps if I warn't a blacksmith's wife, and (what's the same thing) a slave with her apron never off, I should have been to hear the Carols,\" said Mrs. Joe. \"I'm rather partial to Carols, myself, and that's the best of reasons for my never hearing any.\"\n" +
            "\n" +
            "Joe, who had ventured into the kitchen after me as the dust-pan had retired before us, drew the back of his hand across his nose with a conciliatory air when Mrs. Joe darted a look at him, and, when her eyes were withdrawn, secretly crossed his two forefingers, and exhibited them to me, as our token that Mrs. Joe was in a cross temper. This was so much her normal state, that Joe and I would often, for weeks together, be, as to our fingers, like monumental Crusaders as to their legs.\n" +
            "\n" +
            "We were to have a superb dinner, consisting of a leg of pickled pork and greens, and a pair of roast stuffed fowls. A handsome mince-pie had been made yesterday morning (which accounted for the mincemeat not being missed), and the pudding was already on the boil. These extensive arrangements occasioned us to be cut off unceremoniously in respect of breakfast; \"for I an't,\" said Mrs. Joe, \"I an't a-going to have no formal cramming and busting and washing up now, with what I've got before me, I promise you!\"" +
            "So, we had our slices served out, as if we were two thousand troops on a forced march instead of a man and boy at home; and we took gulps of milk and water, with apologetic countenances, from a jug on the dresser. In the meantime, Mrs. Joe put clean white curtains up, and tacked a new flowered-flounce across the wide chimney to replace the old one, and uncovered the little state parlour across the passage, which was never uncovered at any other time, but passed the rest of the year in a cool haze of silver paper, which even extended to the four little white crockery poodles on the mantelshelf, each with a black nose and a basket of flowers in his mouth, and each the counterpart of the other. Mrs. Joe was a very clean housekeeper, but had an exquisite art of making her cleanliness more uncomfortable and unacceptable than dirt itself. Cleanliness is next to Godliness, and some people do the same by their religion.\n" +
            "\n" +
            "My sister having so much to do, was going to church vicariously; that is to say, Joe and I were going. In his working clothes, Joe was a well-knit characteristic-looking blacksmith; in his holiday clothes, he was more like a scarecrow in good circumstances, than anything else. Nothing that he wore then, fitted him or seemed to belong to him; and everything that he wore then, grazed him. On the present festive occasion he emerged from his room, when the blithe bells were going, the picture of misery, in a full suit of Sunday penitentials. As to me, I think my sister must have had some general idea that I was a young offender whom an Accoucheur Policemen had taken up (on my birthday) and delivered over to her, to be dealt with according to the outraged majesty of the law. I was always treated as if I had insisted on being born, in opposition to the dictates of reason, religion, and morality, and against the dissuading arguments of my best friends. Even when I was taken to have a new suit of clothes, the tailor had orders to make them like a kind of Reformatory, and on no account to let me have the free use of my limbs. + Joe and I going to church, therefore, must have been a moving spectacle for compassionate minds. Yet, what I suffered outside, was nothing to what I underwent within. The terrors that had assailed me whenever Mrs. Joe had gone near the pantry, or out of the room, were only to be equalled by the remorse with which my mind dwelt on what my hands had done. Under the weight of my wicked secret, I pondered whether the Church would be powerful enough to shield me from the vengeance of the terrible young man, if I divulged to that establishment. I conceived the idea that the time when the banns were read and when the clergyman said, \"Ye are now to declare it!\" would be the time for me to rise and propose a private conference in the vestry. I am far from being sure that I might not have astonished our small congregation by resorting to this extreme measure, but for its being Christmas Day and no Sunday.\n" +
            "\n" +
            "Mr. Wopsle, the clerk at church, was to dine with us; and Mr. Hubble the wheelwright and Mrs. Hubble; and Uncle Pumblechook (Joe's uncle, but Mrs. Joe appropriated him), who was a well-to-do corn-chandler in the nearest town, and drove his own chaise-cart. The dinner hour was half-past one. When Joe and I got home, we found the table laid, and Mrs. Joe dressed, and the dinner dressing, and the front door unlocked (it never was at any other time) for the company to enter by, and everything most splendid. And still, not a word of the robbery.\n" +
            "\n" +
            "The time came, without bringing with it any relief to my feelings, and the company came. Mr. Wopsle, united to a Roman nose and a large shining bald forehead, had a deep voice which he was uncommonly proud of; indeed it was understood among his acquaintance that if you could only give him his head, he would read the clergyman into fits; he himself confessed that if the Church was \"thrown open,\" meaning to competition, he would not despair of making his mark in it. The Church not being \"thrown open,\" he was, as I have said, our clerk. But he punished the Amens tremendously; and when he gave out the psalm - always giving the whole verse - he looked all round the congregation first, as much as to say, \"You have heard my friend overhead; oblige me with your opinion of this style!\"";

    public static final String MARTIN_EDEN = "The one opened the door with a latch-key and went in, followed by a\n" +
            "young fellow who awkwardly removed his cap. He wore rough clothes\n" +
            "that smacked of the sea, and he was manifestly out of place in the\n" +
            "spacious hall in which he found himself. He did not know what to\n" +
            "do with his cap, and was stuffing it into his coat pocket when the\n" +
            "other took it from him. The act was done quietly and naturally,\n" +
            "and the awkward young fellow appreciated it. \"He understands,\" was\n" +
            "his thought. \"He'll see me through all right.\"\n" +
            "\n" +
            "He walked at the other's heels with a swing to his shoulders, and\n" +
            "his legs spread unwittingly, as if the level floors were tilting up\n" +
            "and sinking down to the heave and lunge of the sea. The wide rooms\n" +
            "seemed too narrow for his rolling gait, and to himself he was in\n" +
            "terror lest his broad shoulders should collide with the doorways or\n" +
            "sweep the bric-a-brac from the low mantel. He recoiled from side\n" +
            "to side between the various objects and multiplied the hazards that\n" +
            "in reality lodged only in his mind. Between a grand piano and a\n" +
            "centre-table piled high with books was space for a half a dozen to\n" +
            "walk abreast, yet he essayed it with trepidation. His heavy arms\n" +
            "hung loosely at his sides. He did not know what to do with those\n" +
            "arms and hands, and when, to his excited vision, one arm seemed\n" +
            "liable to brush against the books on the table, he lurched away\n" +
            "like a frightened horse, barely missing the piano stool. He\n" +
            "watched the easy walk of the other in front of him, and for the\n" +
            "first time realized that his walk was different from that of other\n" +
            "men. He experienced a momentary pang of shame that he should walk\n" +
            "so uncouthly. The sweat burst through the skin of his forehead in\n" +
            "tiny beads, and he paused and mopped his bronzed face with his\n" +
            "handkerchief.\n" +
            "\n" +
            "\"Hold on, Arthur, my boy,\" he said, attempting to mask his anxiety\n" +
            "with facetious utterance. \"This is too much all at once for yours\n" +
            "truly. Give me a chance to get my nerve. You know I didn't want\n" +
            "to come, an' I guess your fam'ly ain't hankerin' to see me\n" +
            "neither.\"\n" +
            "\n" +
            "\"That's all right,\" was the reassuring answer. \"You mustn't be\n" +
            "frightened at us. We're just homely people - Hello, there's a\n" +
            "letter for me.\"\n" +
            "\n" +
            "He stepped back to the table, tore open the envelope, and began to\n" +
            "read, giving the stranger an opportunity to recover himself. And\n" +
            "the stranger understood and appreciated. His was the gift of\n" +
            "sympathy, understanding; and beneath his alarmed exterior that\n" +
            "sympathetic process went on. He mopped his forehead dry and\n" +
            "glanced about him with a controlled face, though in the eyes there\n" +
            "was an expression such as wild animals betray when they fear the\n" +
            "trap. He was surrounded by the unknown, apprehensive of what might\n" +
            "happen, ignorant of what he should do, aware that he walked and\n" +
            "bore himself awkwardly, fearful that every attribute and power of\n" +
            "him was similarly afflicted. He was keenly sensitive, hopelessly\n" +
            "self-conscious, and the amused glance that the other stole privily\n" +
            "at him over the top of the letter burned into him like a dagger-\n" +
            "thrust. He saw the glance, but he gave no sign, for among the\n" +
            "things he had learned was discipline. Also, that dagger-thrust\n" +
            "went to his pride. He cursed himself for having come, and at the\n" +
            "same time resolved that, happen what would, having come, he would\n" +
            "carry it through. The lines of his face hardened, and into his\n" +
            "eyes came a fighting light. He looked about more unconcernedly,\n" +
            "sharply observant, every detail of the pretty interior registering\n" +
            "itself on his brain. His eyes were wide apart; nothing in their\n" +
            "field of vision escaped; and as they drank in the beauty before\n" +
            "them the fighting light died out and a warm glow took its place.\n" +
            "He was responsive to beauty, and here was cause to respond.\n" +
            "\n" +
            "An oil painting caught and held him. A heavy surf thundered and\n" +
            "burst over an outjutting rock; lowering storm-clouds covered the\n" +
            "sky; and, outside the line of surf, a pilot-schooner, close-hauled,\n" +
            "heeled over till every detail of her deck was visible, was surging\n" +
            "along against a stormy sunset sky. There was beauty, and it drew\n" +
            "him irresistibly. He forgot his awkward walk and came closer to\n" +
            "the painting, very close. The beauty faded out of the canvas. His\n" +
            "face expressed his bepuzzlement. He stared at what seemed a\n" +
            "careless daub of paint, then stepped away. Immediately all the\n" +
            "beauty flashed back into the canvas. \"A trick picture,\" was his\n" +
            "thought, as he dismissed it, though in the midst of the\n" +
            "multitudinous impressions he was receiving he found time to feel a\n" +
            "prod of indignation that so much beauty should be sacrificed to\n" +
            "make a trick. He did not know painting. He had been brought up on\n" +
            "chromos and lithographs that were always definite and sharp, near\n" +
            "or far. He had seen oil paintings, it was true, in the show\n" +
            "windows of shops, but the glass of the windows had prevented his\n" +
            "eager eyes from approaching too near.\n" +
            "\n" +
            "He glanced around at his friend reading the letter and saw the\n" +
            "books on the table. Into his eyes leaped a wistfulness and a\n" +
            "yearning as promptly as the yearning leaps into the eyes of a\n" +
            "starving man at sight of food. An impulsive stride, with one lurch\n" +
            "to right and left of the shoulders, brought him to the table, where\n" +
            "he began affectionately handling the books. He glanced at the\n" +
            "titles and the authors' names, read fragments of text, caressing\n" +
            "the volumes with his eyes and hands, and, once, recognized a book\n" +
            "he had read. For the rest, they were strange books and strange\n" +
            "authors. He chanced upon a volume of Swinburne and began reading\n" +
            "steadily, forgetful of where he was, his face glowing. Twice he\n" +
            "closed the book on his forefinger to look at the name of the\n" +
            "author. Swinburne! he would remember that name. That fellow had\n" +
            "eyes, and he had certainly seen color and flashing light. But who\n" +
            "was Swinburne? Was he dead a hundred years or so, like most of the\n" +
            "poets? Or was he alive still, and writing? He turned to the\n" +
            "title-page . . . yes, he had written other books; well, he would go\n" +
            "to the free library the first thing in the morning and try to get\n" +
            "hold of some of Swinburne's stuff. He went back to the text and\n" +
            "lost himself. He did not notice that a young woman had entered the\n" +
            "room. The first he knew was when he heard Arthur's voice saying:-\n" +
            "\n" +
            "\"Ruth, this is Mr. Eden.\"\n" +
            "\n" +
            "The book was closed on his forefinger, and before he turned he was\n" +
            "thrilling to the first new impression, which was not of the girl,\n" +
            "but of her brother's words. Under that muscled body of his he was\n" +
            "a mass of quivering sensibilities. At the slightest impact of the\n" +
            "outside world upon his consciousness, his thoughts, sympathies, and\n" +
            "emotions leapt and played like lambent flame. He was\n" +
            "extraordinarily receptive and responsive, while his imagination,\n" +
            "pitched high, was ever at work establishing relations of likeness\n" +
            "and difference. \"Mr. Eden,\" was what he had thrilled to - he who\n" +
            "had been called \"Eden,\" or \"Martin Eden,\" or just \"Martin,\" all his\n" +
            "life. And \"MISTER!\" It was certainly going some, was his internal\n" +
            "comment. His mind seemed to turn, on the instant, into a vast\n" +
            "camera obscura, and he saw arrayed around his consciousness endless\n" +
            "pictures from his life, of stoke-holes and forecastles, camps and\n" +
            "beaches, jails and boozing-kens, fever-hospitals and slum streets,\n" +
            "wherein the thread of association was the fashion in which he had\n" +
            "been addressed in those various situations.\n" +
            "\n" +
            "And then he turned and saw the girl. The phantasmagoria of his\n" +
            "brain vanished at sight of her. She was a pale, ethereal creature,\n" +
            "with wide, spiritual blue eyes and a wealth of golden hair. He did\n" +
            "not know how she was dressed, except that the dress was as\n" +
            "wonderful as she. He likened her to a pale gold flower upon a\n" +
            "slender stem. No, she was a spirit, a divinity, a goddess; such\n" +
            "sublimated beauty was not of the earth. Or perhaps the books were\n" +
            "right, and there were many such as she in the upper walks of life.\n" +
            "She might well be sung by that chap, Swinburne. Perhaps he had had\n" +
            "somebody like her in mind when he painted that girl, Iseult, in the\n" +
            "book there on the table. All this plethora of sight, and feeling,\n" +
            "and thought occurred on the instant. There was no pause of the\n" +
            "realities wherein he moved. He saw her hand coming out to his, and\n" +
            "she looked him straight in the eyes as she shook hands, frankly,\n" +
            "like a man. The women he had known did not shake hands that way.\n" +
            "For that matter, most of them did not shake hands at all. A flood\n" +
            "of associations, visions of various ways he had made the\n" +
            "acquaintance of women, rushed into his mind and threatened to swamp\n" +
            "it. But he shook them aside and looked at her. Never had he seen\n" +
            "such a woman. The women he had known! Immediately, beside her, on\n" +
            "either hand, ranged the women he had known. For an eternal second\n" +
            "he stood in the midst of a portrait gallery, wherein she occupied\n" +
            "the central place, while about her were limned many women, all to\n" +
            "be weighed and measured by a fleeting glance, herself the unit of\n" +
            "weight and measure. He saw the weak and sickly faces of the girls\n" +
            "of the factories, and the simpering, boisterous girls from the\n" +
            "south of Market. There were women of the cattle camps, and swarthy\n" +
            "cigarette-smoking women of Old Mexico. These, in turn, were\n" +
            "crowded out by Japanese women, doll-like, stepping mincingly on\n" +
            "wooden clogs; by Eurasians, delicate featured, stamped with\n" +
            "degeneracy; by full-bodied South-Sea-Island women, flower-crowned\n" +
            "and brown-skinned. All these were blotted out by a grotesque and\n" +
            "terrible nightmare brood - frowsy, shuffling creatures from the\n" +
            "pavements of Whitechapel, gin-bloated hags of the stews, and all\n" +
            "the vast hell's following of harpies, vile-mouthed and filthy, that\n" +
            "under the guise of monstrous female form prey upon sailors, the\n" +
            "scrapings of the ports, the scum and slime of the human pit.\n" +
            "\n" +
            "\"Won't you sit down, Mr. Eden?\" the girl was saying. \"I have been\n" +
            "looking forward to meeting you ever since Arthur told us. It was\n" +
            "brave of you - \"\n" +
            "\n" +
            "He waved his hand deprecatingly and muttered that it was nothing at\n" +
            "all, what he had done, and that any fellow would have done it. She\n" +
            "noticed that the hand he waved was covered with fresh abrasions, in\n" +
            "the process of healing, and a glance at the other loose-hanging\n" +
            "hand showed it to be in the same condition. Also, with quick,\n" +
            "critical eye, she noted a scar on his cheek, another that peeped\n" +
            "out from under the hair of the forehead, and a third that ran down\n" +
            "and disappeared under the starched collar. She repressed a smile\n" +
            "at sight of the red line that marked the chafe of the collar\n" +
            "against the bronzed neck. He was evidently unused to stiff\n" +
            "collars. Likewise her feminine eye took in the clothes he wore,\n" +
            "the cheap and unaesthetic cut, the wrinkling of the coat across the\n" +
            "shoulders, and the series of wrinkles in the sleeves that\n" +
            "advertised bulging biceps muscles.\n" +
            "\n" +
            "While he waved his hand and muttered that he had done nothing at\n" +
            "all, he was obeying her behest by trying to get into a chair. He\n" +
            "found time to admire the ease with which she sat down, then lurched\n" +
            "toward a chair facing her, overwhelmed with consciousness of the\n" +
            "awkward figure he was cutting. This was a new experience for him.\n" +
            "All his life, up to then, he had been unaware of being either\n" +
            "graceful or awkward. Such thoughts of self had never entered his\n" +
            "mind. He sat down gingerly on the edge of the chair, greatly\n" +
            "worried by his hands. They were in the way wherever he put them.\n" +
            "Arthur was leaving the room, and Martin Eden followed his exit with\n" +
            "longing eyes. He felt lost, alone there in the room with that pale\n" +
            "spirit of a woman. There was no bar-keeper upon whom to call for\n" +
            "drinks, no small boy to send around the corner for a can of beer\n" +
            "and by means of that social fluid start the amenities of friendship\n" +
            "flowing.\n" +
            "\n" +
            "\"You have such a scar on your neck, Mr. Eden,\" the girl was saying.\n" +
            "\"How did it happen? I am sure it must have been some adventure.\"\n" +
            "\n" +
            "\"A Mexican with a knife, miss,\" he answered, moistening his parched\n" +
            "lips and clearing hip throat. \"It was just a fight. After I got\n" +
            "the knife away, he tried to bite off my nose.\"\n" +
            "\n" +
            "Baldly as he had stated it, in his eyes was a rich vision of that\n" +
            "hot, starry night at Salina Cruz, the white strip of beach, the\n" +
            "lights of the sugar steamers in the harbor, the voices of the\n" +
            "drunken sailors in the distance, the jostling stevedores, the\n" +
            "flaming passion in the Mexican's face, the glint of the beast-eyes\n" +
            "in the starlight, the sting of the steel in his neck, and the rush\n" +
            "of blood, the crowd and the cries, the two bodies, his and the\n" +
            "Mexican's, locked together, rolling over and over and tearing up\n" +
            "the sand, and from away off somewhere the mellow tinkling of a\n" +
            "guitar. Such was the picture, and he thrilled to the memory of it,\n" +
            "wondering if the man could paint it who had painted the pilot-\n" +
            "schooner on the wall. The white beach, the stars, and the lights\n" +
            "of the sugar steamers would look great, he thought, and midway on\n" +
            "the sand the dark group of figures that surrounded the fighters.\n" +
            "The knife occupied a place in the picture, he decided, and would\n" +
            "show well, with a sort of gleam, in the light of the stars. But of\n" +
            "all this no hint had crept into his speech. \"He tried to bite off\n" +
            "my nose,\" he concluded.\n" +
            "\n" +
            "\"Oh,\" the girl said, in a faint, far voice, and he noticed the\n" +
            "shock in her sensitive face.\n" +
            "\n" +
            "He felt a shock himself, and a blush of embarrassment shone faintly\n" +
            "on his sunburned cheeks, though to him it burned as hotly as when\n" +
            "his cheeks had been exposed to the open furnace-door in the fire-\n" +
            "room. Such sordid things as stabbing affrays were evidently not\n" +
            "fit subjects for conversation with a lady. People in the books, in\n" +
            "her walk of life, did not talk about such things - perhaps they did\n" +
            "not know about them, either.\n" +
            "\n" +
            "There was a brief pause in the conversation they were trying to get\n" +
            "started. Then she asked tentatively about the scar on his cheek.\n" +
            "Even as she asked, he realized that she was making an effort to\n" +
            "talk his talk, and he resolved to get away from it and talk hers.\n" +
            "\n" +
            "\"It was just an accident,\" he said, putting his hand to his cheek.\n" +
            "\"One night, in a calm, with a heavy sea running, the main-boom-lift\n" +
            "carried away, an' next the tackle. The lift was wire, an' it was\n" +
            "threshin' around like a snake. The whole watch was tryin' to grab\n" +
            "it, an' I rushed in an' got swatted.\"\n" +
            "\n" +
            "\"Oh,\" she said, this time with an accent of comprehension, though\n" +
            "secretly his speech had been so much Greek to her and she was\n" +
            "wondering what a LIFT was and what SWATTED meant.\n" +
            "\n" +
            "\"This man Swineburne,\" he began, attempting to put his plan into\n" +
            "execution and pronouncing the I long.\n" +
            "\n" +
            "\"Who?\"\n" +
            "\n" +
            "\"Swineburne,\" he repeated, with the same mispronunciation. \"The\n" +
            "poet.\"\n" +
            "\n" +
            "\"Swinburne,\" she corrected.\n" +
            "\n" +
            "\"Yes, that's the chap,\" he stammered, his cheeks hot again. \"How\n" +
            "long since he died?\"\n" +
            "\n" +
            "\"Why, I haven't heard that he was dead.\" She looked at him\n" +
            "curiously. \"Where did you make his acquaintance?\"\n" +
            "\n" +
            "\"I never clapped eyes on him,\" was the reply. \"But I read some of\n" +
            "his poetry out of that book there on the table just before you come\n" +
            "in. How do you like his poetry?\"\n" +
            "\n" +
            "And thereat she began to talk quickly and easily upon the subject\n" +
            "he had suggested. He felt better, and settled back slightly from\n" +
            "the edge of the chair, holding tightly to its arms with his hands,\n" +
            "as if it might get away from him and buck him to the floor. He had\n" +
            "succeeded in making her talk her talk, and while she rattled on, he\n" +
            "strove to follow her, marvelling at all the knowledge that was\n" +
            "stowed away in that pretty head of hers, and drinking in the pale\n" +
            "beauty of her face. Follow her he did, though bothered by\n" +
            "unfamiliar words that fell glibly from her lips and by critical\n" +
            "phrases and thought-processes that were foreign to his mind, but\n" +
            "that nevertheless stimulated his mind and set it tingling. Here\n" +
            "was intellectual life, he thought, and here was beauty, warm and\n" +
            "wonderful as he had never dreamed it could be. He forgot himself\n" +
            "and stared at her with hungry eyes. Here was something to live\n" +
            "for, to win to, to fight for - ay, and die for. The books were\n" +
            "true. There were such women in the world. She was one of them.\n" +
            "She lent wings to his imagination, and great, luminous canvases\n" +
            "spread themselves before him whereon loomed vague, gigantic figures\n" +
            "of love and romance, and of heroic deeds for woman's sake - for a\n" +
            "pale woman, a flower of gold. And through the swaying, palpitant\n" +
            "vision, as through a fairy mirage, he stared at the real woman,\n" +
            "sitting there and talking of literature and art. He listened as\n" +
            "well, but he stared, unconscious of the fixity of his gaze or of\n" +
            "the fact that all that was essentially masculine in his nature was\n" +
            "shining in his eyes. But she, who knew little of the world of men,\n" +
            "being a woman, was keenly aware of his burning eyes. She had never\n" +
            "had men look at her in such fashion, and it embarrassed her. She\n" +
            "stumbled and halted in her utterance. The thread of argument\n" +
            "slipped from her. He frightened her, and at the same time it was\n" +
            "strangely pleasant to be so looked upon. Her training warned her\n" +
            "of peril and of wrong, subtle, mysterious, luring; while her\n" +
            "instincts rang clarion-voiced through her being, impelling her to\n" +
            "hurdle caste and place and gain to this traveller from another\n" +
            "world, to this uncouth young fellow with lacerated hands and a line\n" +
            "of raw red caused by the unaccustomed linen at his throat, who, all\n" +
            "too evidently, was soiled and tainted by ungracious existence. She\n" +
            "was clean, and her cleanness revolted; but she was woman, and she\n" +
            "was just beginning to learn the paradox of woman.\n" +
            "\n" +
            "\"As I was saying - what was I saying?\" She broke off abruptly and\n" +
            "laughed merrily at her predicament.\n" +
            "\n" +
            "\"You was saying that this man Swinburne failed bein' a great poet\n" +
            "because - an' that was as far as you got, miss,\" he prompted, while\n" +
            "to himself he seemed suddenly hungry, and delicious little thrills\n" +
            "crawled up and down his spine at the sound of her laughter. Like\n" +
            "silver, he thought to himself, like tinkling silver bells; and on\n" +
            "the instant, and for an instant, he was transported to a far land,\n" +
            "where under pink cherry blossoms, he smoked a cigarette and\n" +
            "listened to the bells of the peaked pagoda calling straw-sandalled\n" +
            "devotees to worship.\n" +
            "\n" +
            "\"Yes, thank you,\" she said. \"Swinburne fails, when all is said,\n" +
            "because he is, well, indelicate. There are many of his poems that\n" +
            "should never be read. Every line of the really great poets is\n" +
            "filled with beautiful truth, and calls to all that is high and\n" +
            "noble in the human. Not a line of the great poets can be spared\n" +
            "without impoverishing the world by that much.\"\n" +
            "\n" +
            "\"I thought it was great,\" he said hesitatingly, \"the little I read.\n" +
            "I had no idea he was such a - a scoundrel. I guess that crops out\n" +
            "in his other books.\"\n" +
            "\n" +
            "\"There are many lines that could be spared from the book you were\n" +
            "reading,\" she said, her voice primly firm and dogmatic.\n" +
            "\n" +
            "\"I must 'a' missed 'em,\" he announced. \"What I read was the real\n" +
            "goods. It was all lighted up an' shining, an' it shun right into\n" +
            "me an' lighted me up inside, like the sun or a searchlight. That's\n" +
            "the way it landed on me, but I guess I ain't up much on poetry,\n" +
            "miss.\"\n" +
            "\n" +
            "He broke off lamely. He was confused, painfully conscious of his\n" +
            "inarticulateness. He had felt the bigness and glow of life in what\n" +
            "he had read, but his speech was inadequate. He could not express\n" +
            "what he felt, and to himself he likened himself to a sailor, in a\n" +
            "strange ship, on a dark night, groping about in the unfamiliar\n" +
            "running rigging. Well, he decided, it was up to him to get\n" +
            "acquainted in this new world. He had never seen anything that he\n" +
            "couldn't get the hang of when he wanted to and it was about time\n" +
            "for him to want to learn to talk the things that were inside of him\n" +
            "so that she could understand. SHE was bulking large on his\n" +
            "horizon.\n" +
            "\n" +
            "\"Now Longfellow - \" she was saying.\n" +
            "\n" +
            "\"Yes, I've read 'm,\" he broke in impulsively, spurred on to exhibit\n" +
            "and make the most of his little store of book knowledge, desirous\n" +
            "of showing her that he was not wholly a stupid clod. \"'The Psalm\n" +
            "of Life,' 'Excelsior,' an' . . . I guess that's all.\"\n" +
            "\n" +
            "She nodded her head and smiled, and he felt, somehow, that her\n" +
            "smile was tolerant, pitifully tolerant. He was a fool to attempt\n" +
            "to make a pretence that way. That Longfellow chap most likely had\n" +
            "written countless books of poetry.\n" +
            "\n" +
            "\"Excuse me, miss, for buttin' in that way. I guess the real facts\n" +
            "is that I don't know nothin' much about such things. It ain't in\n" +
            "my class. But I'm goin' to make it in my class.\"\n" +
            "\n" +
            "It sounded like a threat. His voice was determined, his eyes were\n" +
            "flashing, the lines of his face had grown harsh. And to her it\n" +
            "seemed that the angle of his jaw had changed; its pitch had become\n" +
            "unpleasantly aggressive. At the same time a wave of intense\n" +
            "virility seemed to surge out from him and impinge upon her.\n" +
            "\n" +
            "\"I think you could make it in - in your class,\" she finished with a\n" +
            "laugh. \"You are very strong.\"\n" +
            "\n" +
            "Her gaze rested for a moment on the muscular neck, heavy corded,\n" +
            "almost bull-like, bronzed by the sun, spilling over with rugged\n" +
            "health and strength. And though he sat there, blushing and humble,\n" +
            "again she felt drawn to him. She was surprised by a wanton thought\n" +
            "that rushed into her mind. It seemed to her that if she could lay\n" +
            "her two hands upon that neck that all its strength and vigor would\n" +
            "flow out to her. She was shocked by this thought. It seemed to\n" +
            "reveal to her an undreamed depravity in her nature. Besides,\n" +
            "strength to her was a gross and brutish thing. Her ideal of\n" +
            "masculine beauty had always been slender gracefulness. Yet the\n" +
            "thought still persisted. It bewildered her that she should desire\n" +
            "to place her hands on that sunburned neck. In truth, she was far\n" +
            "from robust, and the need of her body and mind was for strength.\n" +
            "But she did not know it. She knew only that no man had ever\n" +
            "affected her before as this one had, who shocked her from moment to\n" +
            "moment with his awful grammar.\n" +
            "\n" +
            "\"Yes, I ain't no invalid,\" he said. \"When it comes down to hard-\n" +
            "pan, I can digest scrap-iron. But just now I've got dyspepsia.\n" +
            "Most of what you was sayin' I can't digest. Never trained that\n" +
            "way, you see. I like books and poetry, and what time I've had I've\n" +
            "read 'em, but I've never thought about 'em the way you have.\n" +
            "That's why I can't talk about 'em. I'm like a navigator adrift on\n" +
            "a strange sea without chart or compass. Now I want to get my\n" +
            "bearin's. Mebbe you can put me right. How did you learn all this\n" +
            "you've ben talkin'?\"\n" +
            "\n" +
            "\"By going to school, I fancy, and by studying,\" she answered.\n" +
            "\n" +
            "\"I went to school when I was a kid,\" he began to object.\n" +
            "\n" +
            "\"Yes; but I mean high school, and lectures, and the university.\"\n" +
            "\n" +
            "\"You've gone to the university?\" he demanded in frank amazement.\n" +
            "He felt that she had become remoter from him by at least a million\n" +
            "miles.\n" +
            "\n" +
            "\"I'm going there now. I'm taking special courses in English.\"\n" +
            "\n" +
            "He did not know what \"English\" meant, but he made a mental note of\n" +
            "that item of ignorance and passed on.\n" +
            "\n" +
            "\"How long would I have to study before I could go to the\n" +
            "university?\" he asked.\n" +
            "\n" +
            "She beamed encouragement upon his desire for knowledge, and said:\n" +
            "\"That depends upon how much studying you have already done. You\n" +
            "have never attended high school? Of course not. But did you\n" +
            "finish grammar school?\"\n" +
            "\n" +
            "\"I had two years to run, when I left,\" he answered. \"But I was\n" +
            "always honorably promoted at school.\"\n" +
            "\n" +
            "The next moment, angry with himself for the boast, he had gripped\n" +
            "the arms of the chair so savagely that every finger-end was\n" +
            "stinging. At the same moment he became aware that a woman was\n" +
            "entering the room. He saw the girl leave her chair and trip\n" +
            "swiftly across the floor to the newcomer. They kissed each other,\n" +
            "and, with arms around each other's waists, they advanced toward\n" +
            "him. That must be her mother, he thought. She was a tall, blond\n" +
            "woman, slender, and stately, and beautiful. Her gown was what he\n" +
            "might expect in such a house. His eyes delighted in the graceful\n" +
            "lines of it. She and her dress together reminded him of women on\n" +
            "the stage. Then he remembered seeing similar grand ladies and\n" +
            "gowns entering the London theatres while he stood and watched and\n" +
            "the policemen shoved him back into the drizzle beyond the awning.\n" +
            "Next his mind leaped to the Grand Hotel at Yokohama, where, too,\n" +
            "from the sidewalk, he had seen grand ladies. Then the city and the\n" +
            "harbor of Yokohama, in a thousand pictures, began flashing before\n" +
            "his eyes. But he swiftly dismissed the kaleidoscope of memory,\n" +
            "oppressed by the urgent need of the present. He knew that he must\n" +
            "stand up to be introduced, and he struggled painfully to his feet,\n" +
            "where he stood with trousers bagging at the knees, his arms loose-\n" +
            "hanging and ludicrous, his face set hard for the impending ordeal.\n";
}
