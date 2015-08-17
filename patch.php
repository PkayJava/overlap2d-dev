<?php

require_once('vqmod/vqmod.php');
require_once('utilities.php');
VQMod::bootup();

recurse_copy(getcwd() . '/git/modular', getcwd() . '/source/modular');
recurse_copy(getcwd() . '/git/overlap2d', getcwd() . '/source/overlap2d');
recurse_copy(getcwd() . '/git/overlap2d-runtime-libgdx', getcwd() . '/source/overlap2d-runtime-libgdx');
recurse_copy(getcwd() . '/git/VisEditor', getcwd() . '/source/VisEditor');
recurse_copy(getcwd() . '/git/spine-runtimes', getcwd() . '/source/spine-runtimes');
recurse_copy(getcwd() . '/git/ashley', getcwd() . '/source/ashley');
// recurse_copy(getcwd().'/', getcwd(). '/source/overlap2d/art/textures');
recurse_copy(getcwd() . '/git/overlap2d/art', getcwd() . '/source/overlap2d/assets/art');
// recurse_copy(getcwd().'/git/overlap2d/splash', getcwd(). '/source/overlap2d/assets/splash');
// recurse_copy(getcwd().'/git/overlap2d/style', getcwd(). '/source/overlap2d/assets/style');

// copy(getcwd().'/git/VisEditor/UI/assets-raw/icon-folder-parent.png', getcwd().'/source/overlap2d/assets/art/textures/icon-folder-parent.png');

copy(getcwd() . '/src/main/resources/style/uiskin.atlas', getcwd() . '/source/overlap2d/assets/style/uiskin.atlas');
copy(getcwd() . '/src/main/resources/style/uiskin.png', getcwd() . '/source/overlap2d/assets/style/uiskin.png');

patch('8697f4fe92f09f2de8e4e56296a142665f2f955f', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TransformComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TransformComponent.java');
patch('b5e1e805651ef5803fc88562ef216b6f8006ba4d', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NodeComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NodeComponent.java');
patch('b96a5d739e7fa9c5809ff85352a9b1c2fc5745eb', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ParentNodeComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ParentNodeComponent.java');
patch('b7995e0a1f5df1c4cceeb2890c33cf4905a9094d', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/PolygonComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/PolygonComponent.java');
patch('f5f524cdd33bee3b919d6af59eebe4b0dbe556d7', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TextureRegionComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TextureRegionComponent.java');
patch('b0ff39ef373149bd1fba442a790856f0941eb0a8', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physics/PhysicsBodyComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physics/PhysicsBodyComponent.java');
patch('e1d7220f79167c7c4c6a0e8151d64cd7f3f27550', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TintComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TintComponent.java');
patch('60078b950489a9cf0b86f94bc66b7fe7fdc07685', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ViewPortComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ViewPortComponent.java');
patch('b13c510459810c751444f1be0d7fe9a0d08c112e', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/CompositeTransformComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/CompositeTransformComponent.java');
patch('f5c907ff80e9524602cd00e45cfc0f47b5bfbde7', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/label/LabelComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/label/LabelComponent.java');
patch('76af2ba80ba7f359f831021427ecf8de81a4287a', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/light/LightObjectComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/light/LightObjectComponent.java');
patch('e8fbbc9ba561f926f57961bd72f64ac402b77616', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NinePatchComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NinePatchComponent.java');
patch('695283e62abec369c9fbc9c27b2a5f04a328b438', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterDrawerComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterDrawerComponent.java');
patch('bbd935f15b8bc8c48001021616b4137a0d6608f3', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/AnimationComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/AnimationComponent.java');
patch('e909809a2e103cb7192c4d411745e9df4cb975ba', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScissorComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScissorComponent.java');
patch('6e270d2ddaddb74add89cb9ab8e1511975a0f04d', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterComponent.java');
patch('c54bf5296aec41999410a6d0fdd79458ad244ed1', 'git/overlap2d/src/com/uwsoft/editor/view/stage/input/InputListenerComponent.java', 'source/overlap2d/src/com/uwsoft/editor/view/stage/input/InputListenerComponent.java');
patch('debf1b4a899a0d431a14f39bad69ebce0614834d', 'git/overlap2d/src/com/uwsoft/editor/Overlap2D.java', 'source/overlap2d/src/com/uwsoft/editor/Overlap2D.java');
patch('7617bbeeb880b3828b74751647857e5919dd247e', 'git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java', 'source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java');
patch('01bf8053731f17d53aab3dba58d0bce20d92ac6d', 'git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java', 'source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java');
patch('146842d7908a0e60ce32cc54505dc1a87859429b', 'git/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java', 'source/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java');
patch('3d202427351fd343a80614ddd0318270421fff92', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ZIndexComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ZIndexComponent.java');
patch('d98f68ecfcb6f7056a49523dae65fc76b57a48d2', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java');
patch('cbaefda52080edc071df54e2c5079a4699558d69', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationStateComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationStateComponent.java');
patch('aad118af503e16516869f3a702be0b5acc7f7423', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScriptComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScriptComponent.java');
patch('ded13bdd207c348390a77ad5d524981a127ab31d', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/SpineDataComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/SpineDataComponent.java');
patch('1dd81c5366d535f6e3ffb8744e3e41764cae3f9b', 'git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineObjectComponent.java', 'source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineObjectComponent.java');
patch('cf51b58c1ea74a8e28f441f0ec13943e065e6c40', 'git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java', 'source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java');
patch('3e81b61e76b2209535f42386249c80c857dcb344', 'git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineDrawableLogic.java', 'source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineDrawableLogic.java');
patch('ccbcf33f384d1d25ae1f6c88d1a57d1aa55c24b2', 'git/overlap2d/src/com/uwsoft/editor/splash/SplashStarter.java', 'source/overlap2d/src/com/uwsoft/editor/splash/SplashStarter.java');
patch('b75a6a1006488422662237fc8ec17d78d9210adf', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/particle/ParticleComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/particle/ParticleComponent.java');
patch('635d867355d488aeddf9ab944a0003cbfef073c3', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/utils/MySkin.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/utils/MySkin.java');
patch('f93b8da5f547693789818813dd3b127ac63eb5ba', 'git/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java', 'source/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java');
patch('245ee1698ce75636a02398f0ac69675bc28ba90d', 'git/overlap2d/src/com/uwsoft/editor/Main.java', 'source/overlap2d/src/com/uwsoft/editor/Main.java');
patch('73a65fb62af12ec70ac60b367325f140ac894065', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/LayerMapComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/LayerMapComponent.java');
patch('aa34caa3e21e34690c55cdcf6cd80cc757a46f4e', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/MainItemComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/MainItemComponent.java');
patch('285488b4568063e772694e724290d356002e9640', 'git/overlap2d/assets/style/uiskin.json', 'source/overlap2d/assets/style/uiskin.json');
patch('0d79ff76abf99f97ffd11ceea73da3b27b87e069', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/additional/ButtonComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/additional/ButtonComponent.java');
patch('5302644c5bc93d629e294cbcd93fc3f42ec61b97', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/DimensionsComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/DimensionsComponent.java');
patch('cc32e0b8283140e0ca35186bd51abd90ab01e1e0', 'git/overlap2d/src/com/uwsoft/editor/controller/BootstrapPlugins.java', 'source/overlap2d/src/com/uwsoft/editor/controller/BootstrapPlugins.java');
patch('72b6bdd77c3369dd2129bd288464bfb27a100b5d', 'git/overlap2d/src/com/uwsoft/editor/data/vo/EditorConfigVO.java', 'source/overlap2d/src/com/uwsoft/editor/data/vo/EditorConfigVO.java');
patch('5343ff57092fdced5ab9f4487abafbb94da1d8fd', 'git/overlap2d/src/com/uwsoft/editor/data/vo/ProjectVO.java', 'source/overlap2d/src/com/uwsoft/editor/data/vo/ProjectVO.java');
patch('a08e671def03491696e86095f9c18fb7b99f42ec', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/SceneVO.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/SceneVO.java');
patch('cbf02d7efb2d19eec7fc8481b64119d3414c705d', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/ProjectInfoVO.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/ProjectInfoVO.java');

recurse_copy(getcwd() . '/source/overlap2d/plugins/performance/src/com/overlap2d', getcwd() . '/source/overlap2d/src/com/overlap2d');

/**
 * patch('','git/overlap2d/src/','source/overlap2d/src/');
 * patch('','git/overlap2d-runtime-libgdx/src/','source/overlap2d-runtime-libgdx/src/');
 * patch('','git/overlap2d-runtime-libgdx/extensions/spine/src/','source/overlap2d-runtime-libgdx/extensions/spine/src/');
 */
