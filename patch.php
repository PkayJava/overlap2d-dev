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
recurse_copy(getcwd() . '/git/overlap2d/art', getcwd() . '/source/overlap2d/assets/art');

copy(getcwd() . '/src/main/resources/style/uiskin.atlas', getcwd() . '/source/overlap2d/assets/style/uiskin.atlas');
copy(getcwd() . '/src/main/resources/style/uiskin.png', getcwd() . '/source/overlap2d/assets/style/uiskin.png');

patch('7617bbeeb880b3828b74751647857e5919dd247e', 'git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java', 'source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/CustomColorPicker.java');
patch('01bf8053731f17d53aab3dba58d0bce20d92ac6d', 'git/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java', 'source/overlap2d/src/com/uwsoft/editor/view/ui/widget/components/color/ColorChannelWidget.java');
patch('cd9333e5e357976d252f741533c09ed5b13a8991','git/overlap2d/src/com/uwsoft/editor/view/ItemControlMediator.java', 'source/overlap2d/src/com/uwsoft/editor/view/ItemControlMediator.java');
patch('635d867355d488aeddf9ab944a0003cbfef073c3', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/utils/MySkin.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/utils/MySkin.java');
patch('a08e671def03491696e86095f9c18fb7b99f42ec', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/SceneVO.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/SceneVO.java');
patch('cbf02d7efb2d19eec7fc8481b64119d3414c705d', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/ProjectInfoVO.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/data/ProjectInfoVO.java');
patch('d98f68ecfcb6f7056a49523dae65fc76b57a48d2', 'git/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java', 'source/overlap2d-runtime-libgdx/src/com/uwsoft/editor/renderer/components/sprite/SpriteAnimationComponent.java');
patch('f93b8da5f547693789818813dd3b127ac63eb5ba', 'git/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java', 'source/overlap2d/src/com/uwsoft/editor/proxy/FontManager.java');
patch('245ee1698ce75636a02398f0ac69675bc28ba90d', 'git/overlap2d/src/com/uwsoft/editor/Main.java', 'source/overlap2d/src/com/uwsoft/editor/Main.java');
patch('5343ff57092fdced5ab9f4487abafbb94da1d8fd', 'git/overlap2d/src/com/uwsoft/editor/data/vo/ProjectVO.java', 'source/overlap2d/src/com/uwsoft/editor/data/vo/ProjectVO.java');
patch('72b6bdd77c3369dd2129bd288464bfb27a100b5d', 'git/overlap2d/src/com/uwsoft/editor/data/vo/EditorConfigVO.java', 'source/overlap2d/src/com/uwsoft/editor/data/vo/EditorConfigVO.java');
patch('87b803fa68faecec4048da44681a6bb5d7c509b6', 'git/overlap2d/src/com/uwsoft/editor/controller/BootstrapPlugins.java', 'source/overlap2d/src/com/uwsoft/editor/controller/BootstrapPlugins.java');
patch('146842d7908a0e60ce32cc54505dc1a87859429b', 'git/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java', 'source/overlap2d/src/com/puremvc/patterns/mediator/SimpleMediator.java');
patch('cf51b58c1ea74a8e28f441f0ec13943e065e6c40', 'git/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java', 'source/overlap2d-runtime-libgdx/extensions/spine/src/com/overlap2d/extensions/spine/SpineSystem.java');
patch('285488b4568063e772694e724290d356002e9640', 'git/overlap2d/assets/style/uiskin.json', 'source/overlap2d/assets/style/uiskin.json');

recurse_copy(getcwd() . '/source/overlap2d/plugins/performance/src/com/overlap2d', getcwd() . '/source/overlap2d/src/com/overlap2d');
recurse_copy(getcwd() . '/source/overlap2d/plugins/nine-patch/src/com/overlap2d', getcwd() . '/source/overlap2d/src/com/overlap2d');

/**
 * patch('','git/overlap2d/src/','source/overlap2d/src/');
 * patch('','git/overlap2d-runtime-libgdx/src/','source/overlap2d-runtime-libgdx/src/');
 * patch('','git/overlap2d-runtime-libgdx/extensions/spine/src/','source/overlap2d-runtime-libgdx/extensions/spine/src/');
 */
