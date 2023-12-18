using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using Unity.Netcode;

public class PlayerMovement : NetworkBehaviour
{

    [Header("Player Properties")]
    [SerializeField] private float legLength;

    [Header("Camera")]
    public Camera cam;
    public Vector3 cameraOffset;
    public float mouseSensitivity;
    [SerializeField] private float mouseX;
    [SerializeField] private float mouseY;
    private float xRotation = 0f;
    private float yRotation = 0f;

    [Header("Movement")]
    public CharacterController controller;
    public LayerMask groundMask;
    [SerializeField] private float movementSpeed;
    [SerializeField] private float walkSpeed;
    [SerializeField] private float sprintSpeed;
    [SerializeField] private float sprintTime = 0f;
    [SerializeField] private float maxSprintTime;
    [SerializeField] private float sprintCoolDownTime;
    [SerializeField] private float jumpForce;
    [SerializeField] private bool isCrouching;
    [SerializeField] private bool isSprinting;
    [SerializeField] private bool canSprint;
    [SerializeField] private bool isGrounded;
    private float moveX;
    private float moveZ;
    public float gravity = -9.81f;
    private Vector3 velocity;

    // Start is called before the first frame update
    void Start()
    {
        if (!IsOwner) return;
        Spawn(new Vector3(-90, 2, -90));
        Cursor.lockState = CursorLockMode.Locked;
        movementSpeed = walkSpeed;
        cam = Camera.main;
        playerName = cam.GetComponent<MainMenuScript>().playerName;
        eventSystem = GameObject.Find("EventSystem");
        controller = GetComponent<CharacterController>();
    }

    // Update is called once per frame
    void Update()
    {
        if (!IsOwner) return;

        ChangeTeamProperties();
        Look();
        Move();
        Hit();
        Interact();
    }

    void Look()
    {
        cam.transform.position = gameObject.transform.position + cameraOffset;
        mouseX = Input.GetAxis("Mouse X") * mouseSensitivity * Time.deltaTime;
        mouseY = Input.GetAxis("Mouse Y") * mouseSensitivity * Time.deltaTime;

        xRotation -= mouseY;
        xRotation = Mathf.Clamp(xRotation, -90f, 90f);
        yRotation += mouseX;
        cam.transform.localRotation = Quaternion.Euler(xRotation, yRotation, 0f);
        //camera.transform.Rotate(Vector3.up * mouseX);
        transform.Rotate(Vector3.up * mouseX);
        //camera.transform.Rotate(xRotation, mouseX, 0f);
    }

    void Move()
    {
        // Movement
        // X and Z Axis
        moveX = Input.GetAxis("Horizontal");
        moveZ = Input.GetAxis("Vertical");
        Vector3 move = transform.right * moveX + transform.forward * moveZ;


        // Sprinting
        sprintTime = Mathf.Clamp(sprintTime, 0f, maxSprintTime);
        if (isSprinting && sprintTime > 0 && canSprint)
        {
            movementSpeed = sprintSpeed;
            sprintTime -= Time.deltaTime;
            cam.GetComponent<MainMenuScript>().UpdateStamina(maxSprintTime, sprintTime);
        }
        else
        {
            movementSpeed = walkSpeed;
            sprintTime += Time.deltaTime;
            canSprint = false;
            cam.GetComponent<MainMenuScript>().UpdateStamina(maxSprintTime, sprintTime);
            if (sprintTime >= sprintCoolDownTime) canSprint = true;
        }
        controller.Move(move * movementSpeed * Time.deltaTime);

        // Jumping
        isGrounded = Physics.Raycast(transform.position, Vector3.down, legLength, groundMask);

        if (Input.GetButtonDown("Jump") && isGrounded)
        {
            velocity.y = Mathf.Sqrt(jumpForce * -2f * gravity);
        }

        velocity.y += gravity * Time.deltaTime;
        controller.Move(velocity * Time.deltaTime);

        if (isGrounded && velocity.y < 0)
        {
            velocity.y = -2f;
        }

        if (isCrouching)
        {
            cameraOffset = new Vector3(0, 0, 0);
            transform.localScale = new Vector3(1, 0.7f, 1);
        }
        else
        {
            cameraOffset = new Vector3(0, 0.5f, 0);
            transform.localScale = new Vector3(1, 1, 1);
        }
    }

}
