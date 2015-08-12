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

patch('83c8b5e1c74e6a2fe429c4216955b3ba259e83d2', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TransformComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TransformComponent.java');
patch('92ee7e6edd6d66f01aeb054320fe05cd5b2803c1', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NodeComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NodeComponent.java');
patch('48419a2bb5f64a78d81fc3577ac8c7da917a4c16', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ParentNodeComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ParentNodeComponent.java');
patch('36c88654cb3cfb5ac1d51d4efb95f5998daffe38', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/PolygonComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/PolygonComponent.java');
patch('5865e4bfc70d7828cbd78e24c49f51efad2e31a8', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TextureRegionComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TextureRegionComponent.java');
patch('26e3644229bf583bd426ed28c22acd706db72687', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physic/PhysicsBodyComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/physic/PhysicsBodyComponent.java');
patch('dc5eba20a1fa4bf1d4b07694405a561c3ff03aa6', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TintComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/TintComponent.java');
patch('b58f39adf4dd57dbd8d9ad0b4edb47991ce42301', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ViewPortComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ViewPortComponent.java');
patch('9bc3a217ac285b45c8cb9f88315666ea6202ce51', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/CompositeTransformComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/CompositeTransformComponent.java');
patch('3b1aef744b18d5c6a19f499ea265af2750ecc86b', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/label/LabelComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/label/LabelComponent.java');
patch('798c7c0cc4accfcf3f3cb9ac05c28887f9d7d84b', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/light/LightObjectComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/light/LightObjectComponent.java');
patch('17074a9691b1bc06935c3105942f8d30642b9293', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NinePatchComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/NinePatchComponent.java');
patch('8b5fa78f0c0108d1eaf7fb383f923c9dcac82d04', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterDrawerComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterDrawerComponent.java');
patch('0eb3936cf97b67385e61ca227ff879ab4690f3a8', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/AnimationComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/AnimationComponent.java');
patch('9a78a52d2911601e3515fd7ded491b8db78c8670', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScissorComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScissorComponent.java');
patch('34d68c4ba9ba4348af43835ef871b15975f4d5e9', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/spriter/SpriterComponent.java');
patch('2c404a4c7ff898d5c03a20d62e976ffb8bff3443', 'git/overlap2d/src/com/uwsoft/editor/view/stage/input/InputListenerComponent.java', 'source/overlap2d/src/com/uwsoft/editor/view/stage/input/InputListenerComponent.java');
patch('debf1b4a899a0d431a14f39bad69ebce0614834d', 'git/overlap2d/src/com/uwsoft/editor/Overlap2D.java', 'source/overlap2d/src/com/uwsoft/editor/Overlap2D.java');
patch('7617bbeeb880b3828b74751647857e5919dd247e', 'git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java', 'source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java');
patch('01bf8053731f17d53aab3dba58d0bce20d92ac6d', 'git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java', 'source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java');
patch('146842d7908a0e60ce32cc54505dc1a87859429b', 'git/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java', 'source/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java');
patch('78d8cf8bf6205043174f834ef85f8939b7c29415', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ZIndexComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ZIndexComponent.java');
patch('9e495ac3b1725f9c8795657efbdb6587b98a8512', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java');
patch('93409c4869d03fe793c37baa27d542ce93d037d4', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationStateComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationStateComponent.java');
patch('1fcc66139ab8742fc10c50a2b6d6044b94a35a29', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScriptComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/ScriptComponent.java');
patch('5332f117601dafa03f81275bfec905bf6e3efb77', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/SpineDataComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/SpineDataComponent.java');
patch('fabb8614baa97558e8c1cc14db91a972f8caf90b', 'git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineObjectComponent.java', 'source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineObjectComponent.java');
patch('cf51b58c1ea74a8e28f441f0ec13943e065e6c40', 'git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java', 'source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java');
patch('3e81b61e76b2209535f42386249c80c857dcb344', 'git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineDrawableLogic.java', 'source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineDrawableLogic.java');
patch('ccbcf33f384d1d25ae1f6c88d1a57d1aa55c24b2', 'git/overlap2d/src/com/uwsoft/editor/splash/SplashStarter.java', 'source/overlap2d/src/com/uwsoft/editor/splash/SplashStarter.java');
patch('db248bbf472fccd9cc869fb372e0d7c186d5869a', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/particle/ParticleComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/particle/ParticleComponent.java');
patch('635d867355d488aeddf9ab944a0003cbfef073c3', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/utils/MySkin.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/utils/MySkin.java');
patch('f93b8da5f547693789818813dd3b127ac63eb5ba', 'git/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java', 'source/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java');
patch('245ee1698ce75636a02398f0ac69675bc28ba90d', 'git/overlap2d/src/com/uwsoft/editor/Main.java', 'source/overlap2d/src/com/uwsoft/editor/Main.java');
patch('302d735d5bc8a8245114a85f023d1f3438bef27a', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/LayerMapComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/LayerMapComponent.java');
patch('1b71044bb831c593e40c041e8ce55690775bae57', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/MainItemComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/MainItemComponent.java');
patch('285488b4568063e772694e724290d356002e9640', 'git/overlap2d/assets/style/uiskin.json', 'source/overlap2d/assets/style/uiskin.json');
patch('f2b74be611b6afb43c2aba330ce579e031755773', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/additional/ButtonComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/additional/ButtonComponent.java');
patch('c03b158da832d1ba98011a598781b4a1d10a3371', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/DimensionsComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/DimensionsComponent.java');
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
